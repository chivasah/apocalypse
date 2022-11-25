package zw.co.henry.indomidas.apocalypse.api.reports;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import zw.co.henry.indomidas.apocalypse.dto.converters.SurvivorDTOFromSurvivorConverter;
import zw.co.henry.indomidas.apocalypse.dto.converters.SurvivorDetailDTOFromSurvivorDetailConverter;
import zw.co.henry.indomidas.apocalypse.dto.converters.SurvivorFromSurvivorDTOConverter;
import zw.co.henry.indomidas.apocalypse.model.response.NumericResponse;
import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;
import zw.co.henry.indomidas.apocalypse.model.survivor.QSurvivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.QSurvivorDetail;
import zw.co.henry.indomidas.apocalypse.model.survivor.Survivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorResponse;
import zw.co.henry.indomidas.apocalypse.repo.SurvivorRepo;

/**
 * @author henry
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@Api(tags = { "Reports" })
@Slf4j
public class ReportsController
{

   private final SurvivorDetailDTOFromSurvivorDetailConverter dtoFromSurvivorDetailConverter = new SurvivorDetailDTOFromSurvivorDetailConverter();
   private final SurvivorDTOFromSurvivorConverter dtoFromSurvivorConverter = new SurvivorDTOFromSurvivorConverter();
   private final SurvivorFromSurvivorDTOConverter survivorFromDTOConverter = new SurvivorFromSurvivorDTOConverter();

   private final QSurvivor qSurvivor = QSurvivor.survivor;
   private final QSurvivorDetail qSurvivorDetail = QSurvivorDetail.survivorDetail;

   @PersistenceContext(unitName = "default")
   private EntityManager entityManager;

   @Autowired
   private SurvivorRepo survivorRepo;

//   @ApiOperation(value = "Percentage of infected survivors.", response = NumericResponse.class)
   @RequestMapping(value = "/percentage-of-infected-survivors", method = RequestMethod.GET)
   public NumericResponse getPercentageOfInfectedSurvivors(@RequestParam(value = "category", required = false)
   String category, Pageable pageable)
   {
      NumericResponse resp = new NumericResponse();
      Survivor qry = new Survivor();

       final QSurvivor qSurvivor0 = new QSurvivor("s0");
       final QSurvivorDetail qSurvivorDetail0 = new QSurvivorDetail("s1");

       final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);

       final List<Tuple> infectedSurvivors = jpaQueryFactory
           .select(qSurvivorDetail0.survivor().id, Wildcard.countAsInt).from(qSurvivorDetail0)
           .where(qSurvivorDetail0.infected.isTrue())
           .groupBy(qSurvivorDetail0.survivor().id).having(qSurvivorDetail0.survivor().id.count().goe(3))
           .fetch();

       log.info("infectedSurvivors: {}", infectedSurvivors.size());
       long totalItemCount = jpaQueryFactory.selectFrom(qSurvivor).fetchCount();
       log.info("totalItemCount: {}", totalItemCount);

       double percentage = (double) infectedSurvivors.size()/(double)totalItemCount;
       //        if (category  != null)  { qry.setCategory(category); }
      resp.setResult(percentage);
      resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
      resp.setOperationMessage("Percentage of infected survivors");
      return resp;
   }

//   @ApiOperation(value = "Percentage of non-infected survivors.", response = NumericResponse.class)
   @RequestMapping(value = "/percentage-of-non-infected-survivors", method = RequestMethod.GET)
   public NumericResponse getPercentageOfNonInfectedSurvivors(@RequestParam(value = "category", required = false)
   String category, Pageable pageable)
   {
       NumericResponse resp = new NumericResponse();
       Survivor qry = new Survivor();

       final QSurvivor qSurvivor0 = new QSurvivor("s0");
       final QSurvivorDetail qSurvivorDetail0 = new QSurvivorDetail("s1");

       final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);

       final List<Tuple> infectedSurvivors = jpaQueryFactory
           .select(qSurvivorDetail0.survivor().id, Wildcard.countAsInt).from(qSurvivorDetail0)
           .where(qSurvivorDetail0.infected.isTrue())
           .groupBy(qSurvivorDetail0.survivor().id).having(qSurvivorDetail0.survivor().id.count().goe(3))
           .fetch();

       log.info("infectedSurvivors: {}", infectedSurvivors.size());
       long totalItemCount = jpaQueryFactory.selectFrom(qSurvivor).fetchCount();
       log.info("totalItemCount: {}", totalItemCount);

       double percentage = (double) (totalItemCount - infectedSurvivors.size())/(double)totalItemCount;
       //        if (category  != null)  { qry.setCategory(category); }
       resp.setResult(percentage);
       resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
       resp.setOperationMessage("Percentage of non-infected survivors");
       return resp;
   }

//   @ApiOperation(value = "List of infected survivors", response = SurvivorResponse.class)
   @RequestMapping(value = "/list-of-infected-survivors", method = RequestMethod.GET)
   public SurvivorResponse getListOfInfectedSurvivorsByPage(
//           @ApiParam(value = "")
           @RequestParam(value = "page", defaultValue = "0", required = false)
   Integer page,
//           @ApiParam(value = "between 1 to 1000")
           @RequestParam(value = "size", defaultValue = "20", required = false)
   Integer size, @RequestParam(value = "category", required = false)
   String category, Pageable pageable)
   {
      SurvivorResponse resp = new SurvivorResponse();
      Survivor qry = new Survivor();
      //        if (category  != null)  { qry.setCategory(category); }


       final QSurvivor qSurvivor0 = new QSurvivor("s0");
       final QSurvivorDetail qSurvivorDetail0 = new QSurvivorDetail("s1");

      final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
      final JPAQuery<Integer> infectedSurvivors = new JPAQuery<Integer>(entityManager).select(qSurvivorDetail0.survivor().id).from(qSurvivorDetail0)
                                                                                      .join(qSurvivorDetail0.survivor(), qSurvivor0)
                                                                                      .where(qSurvivorDetail0.infected.isTrue())
                                                                                      .groupBy(qSurvivorDetail0.survivor().id)
                                                                                      .having(Wildcard.countAsInt.goe(3));

       List<Integer> itemIds = infectedSurvivors.fetch();
       log.info("itemIds: {}", itemIds);
      List<Survivor> res = jpaQueryFactory.selectFrom(qSurvivor).where(qSurvivor.id.in(itemIds)).fetch();

      Page<Survivor> survivorPage = new PageImpl<>(res, pageable, itemIds.size());
      log.info("res: {}", res);
      resp.setPageStats(survivorPage, true);
      resp.setItems(survivorPage.getContent().stream().map(dtoFromSurvivorConverter::convert).collect(Collectors.toList()));
      return resp;
   }

//   @ApiOperation(value = "List of non-infected survivors", response = SurvivorResponse.class)
   @RequestMapping(value = "/list-of-non-infected-survivors", method = RequestMethod.GET)
   public SurvivorResponse getListOfNonInfectedSurvivorsByPage(
//           @ApiParam(value = "")
           @RequestParam(value = "page", defaultValue = "0", required = false)
   Integer page,
//           @ApiParam(value = "between 1 to 1000")
           @RequestParam(value = "size", defaultValue = "20", required = false)
   Integer size, @RequestParam(value = "category", required = false)
   String category, Pageable pageable)
   {
       SurvivorResponse resp = new SurvivorResponse();
       Survivor qry = new Survivor();
       //        if (category  != null)  { qry.setCategory(category); }


       final QSurvivor qSurvivor0 = new QSurvivor("s0");
       final QSurvivorDetail qSurvivorDetail0 = new QSurvivorDetail("s1");

       final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
       final JPAQuery<Integer> infectedSurvivors = new JPAQuery<Integer>(entityManager).select(qSurvivorDetail0.survivor().id).from(qSurvivorDetail0)
           .join(qSurvivorDetail0.survivor(), qSurvivor0)
           .where(qSurvivorDetail0.infected.isTrue())
           .groupBy(qSurvivorDetail0.survivor().id)
           .having(Wildcard.countAsInt.goe(3));

       List<Integer> itemIds = infectedSurvivors.fetch();
       log.info("itemIds: {}", itemIds);
       List<Survivor> res = jpaQueryFactory.selectFrom(qSurvivor).where(qSurvivor.id.notIn(itemIds)).fetch();

       Page<Survivor> survivorPage = new PageImpl<>(res, pageable, itemIds.size());
       log.info("res: {}", res);
       resp.setPageStats(survivorPage, true);
       resp.setItems(survivorPage.getContent().stream().map(dtoFromSurvivorConverter::convert).collect(Collectors.toList()));
       return resp;
   }

//   @ApiOperation(value = "List of robots", response = SurvivorResponse.class)
   @RequestMapping(value = "/list-of-robots", method = RequestMethod.GET)
   public SurvivorResponse getListOfRobotsByPage(
//           @ApiParam(value = "")
           @RequestParam(value = "page", defaultValue = "0", required = false)
   Integer page,
//           @ApiParam(value = "between 1 to 1000")
           @RequestParam(value = "size", defaultValue = "20", required = false)
   Integer size, @RequestParam(value = "category", required = false)
   String category, Pageable pageable)
   {
      SurvivorResponse resp = new SurvivorResponse();
      Survivor qry = new Survivor();
      if (category != null) {
         //            qry.setId(category);
      }
      //        if (category  != null)  { qry.setCategory(category); }

      Page<Survivor> survivorPage = survivorRepo.findAll(org.springframework.data.domain.Example.of(qry), pageable);
      resp.setPageStats(survivorPage, true);
      resp.setItems(survivorPage.getContent().stream().map(dtoFromSurvivorConverter::convert).collect(Collectors.toList()));
      return resp;
   }
}
