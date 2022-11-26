package zw.co.henry.indomidas.apocalypse.service.impl;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.dto.converters.SurvivorDTOFromSurvivorConverter;
import zw.co.henry.indomidas.apocalypse.model.response.NumericResponse;
import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;
import zw.co.henry.indomidas.apocalypse.model.survivor.QSurvivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.QSurvivorDetail;
import zw.co.henry.indomidas.apocalypse.model.survivor.Survivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorResponse;
import zw.co.henry.indomidas.apocalypse.repo.SurvivorRepo;
import zw.co.henry.indomidas.apocalypse.service.SurvivorService;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum.ERROR;
import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum.SUCCESS;

/**
 * @author henry
 */
@Service
@Slf4j
public class SurvivorServiceImpl implements SurvivorService {

    private final SurvivorDTOFromSurvivorConverter dtoFromSurvivorConverter = new SurvivorDTOFromSurvivorConverter();

    private final QSurvivor qSurvivor = QSurvivor.survivor;

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Autowired
    private SurvivorRepo survivorRepo;

    @Override
    public SurvivorResponse getListOfNonInfectedSurvivorsByPage(final String category$, final Pageable pageable$) {
        log.debug("getListOfNonInfectedSurvivorsByPage({})", category$);

        final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
        final JPAQuery<Integer> infectedSurvivors = infectedSurvivors();
        final List<Integer> itemIds = infectedSurvivors.fetch();
        log.info("itemIds: {}", itemIds);
        List<Survivor> content = jpaQueryFactory.selectFrom(qSurvivor).where(qSurvivor.id.notIn(itemIds)).fetch();

        Page<Survivor> page = new PageImpl<>(content, pageable$, itemIds.size());

        final SurvivorResponse response = new SurvivorResponse();
        response.setPageStats(page, true);
        response.setItems(page.getContent().stream().map(dtoFromSurvivorConverter::convert).collect(Collectors.toList()));
        response.setOperationMessage(MessageFormat.format("Survivor page {0} of {1} // {2} robots found", pageable$.getPageNumber(), page.getTotalPages(), page.getTotalElements()));
        return response;
    }

    @Override
    public SurvivorResponse getListOfInfectedSurvivorsByPage(@Nullable final String category$, final Pageable pageable$) {
        log.debug("getListOfInfectedSurvivorsByPage({})", category$);

        final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
        final JPAQuery<Integer> infectedSurvivors = infectedSurvivors();
        final List<Integer> itemIds = infectedSurvivors.fetch();
        log.info("itemIds: {}", itemIds);
        List<Survivor> content = jpaQueryFactory.selectFrom(qSurvivor).where(qSurvivor.id.in(itemIds)).fetch();

       final Page<Survivor> page = new PageImpl<>(content, pageable$, itemIds.size());
        final SurvivorResponse response = new SurvivorResponse();
        response.setPageStats(page, true);
        response.setItems(page.getContent().stream().map(dtoFromSurvivorConverter::convert).collect(Collectors.toList()));
        response.setOperationMessage(MessageFormat.format("Survivor page {0} of {1} // {2} robots found", pageable$.getPageNumber(), page.getTotalPages(), page.getTotalElements()));
        return response;
    }

    @Override
    public NumericResponse getPercentageOfInfectedSurvivors(@Nullable final String category$, final Pageable pageable$) {
        log.debug("getPercentageOfInfectedSurvivors({})", category$);

        final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);

        final List<Integer> infectedSurvivors = infectedSurvivors().fetch();

        log.info("infectedSurvivors: {}", infectedSurvivors.size());
        long totalItemCount = jpaQueryFactory.selectFrom(qSurvivor).fetchCount();
        log.info("totalItemCount: {}", totalItemCount);

        double percentage = (double) infectedSurvivors.size() / (double) totalItemCount;
        //        if (category  != null)  { qry.setCategory(category); }
        NumericResponse response = new NumericResponse();
        response.setResult(percentage);
        response.setOperationStatus(SUCCESS);
        response.setOperationMessage("Percentage of infected survivors");
        return response;
    }

    @Override
    public NumericResponse getPercentageOfNonInfectedSurvivors(final String category$, final Pageable pageable$) {
        log.debug("getPercentageOfNonInfectedSurvivors({})", category$);

        final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);

        final List<Integer> infectedSurvivors = infectedSurvivors().fetch();

        log.info("infectedSurvivors: {}", infectedSurvivors.size());
        long totalItemCount = jpaQueryFactory.selectFrom(qSurvivor).fetchCount();
        log.info("totalItemCount: {}", totalItemCount);

        double percentage = (double) (totalItemCount - infectedSurvivors.size()) / (double) totalItemCount;
        //        if (category  != null)  { qry.setCategory(category); }
        NumericResponse response = new NumericResponse();
        response.setResult(percentage);
        response.setOperationStatus(SUCCESS);
        response.setOperationMessage("Percentage of non-infected survivors");
        return response;
    }

    @Override
    public OperationResponse updateSurvivorLocation(final SurvivorDTO survivorDTO$) {
        log.debug("updateSurvivorLocation({})", survivorDTO$);

        OperationResponse response = new OperationResponse();
        Survivor qry = new Survivor();
        qry.setId(survivorDTO$.getId());

        if (this.survivorRepo.exists( Example.of(qry))) {
            Survivor survivor = this.survivorRepo.findOneById(survivorDTO$.getId()).get();
            survivor.setLastLocation(survivorDTO$.getLastLocation());
            this.survivorRepo.saveAndFlush(survivor);
            response.setOperationStatus(SUCCESS);
            response.setOperationMessage("Survivor location updated");
        }
        else {
            response.setOperationStatus(ERROR);
            response.setOperationMessage("No Survivor Exist");
        }
        return response;
    }

    private JPAQuery<Integer> infectedSurvivors() {
        final QSurvivor qSurvivor0 = new QSurvivor("s0");
        final QSurvivorDetail qSurvivorDetail0 = new QSurvivorDetail("s1");

        return new JPAQuery<Integer>(entityManager).select(qSurvivorDetail0.survivor().id).from(qSurvivorDetail0)
                .join(qSurvivorDetail0.survivor(), qSurvivor0)
                .where(qSurvivorDetail0.infected.isTrue())
                .groupBy(qSurvivorDetail0.survivor().id)
                .having(Wildcard.countAsInt.goe(3));
    }
}
