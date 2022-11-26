package zw.co.henry.indomidas.apocalypse.api.survivor;

import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.Wildcard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import zw.co.henry.indomidas.apocalypse.model.data.SingleSeries;
import zw.co.henry.indomidas.apocalypse.model.response.SingleDataSeriesResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import zw.co.henry.indomidas.apocalypse.model.robot.Robot;
import zw.co.henry.indomidas.apocalypse.model.robot.QRobot;
import zw.co.henry.indomidas.apocalypse.model.survivor.QSurvivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.Survivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorDetail;
import zw.co.henry.indomidas.apocalypse.model.survivor.QSurvivorDetail;
import zw.co.henry.indomidas.apocalypse.repo.SurvivorRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author henry
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Survivors" })
@Slf4j
public class SurvivorStatsController
{
    private final QSurvivor qSurvivor = QSurvivor.survivor;

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

   @ApiOperation(value = "Survivor Stats", response = SingleDataSeriesResponse.class)
   @RequestMapping(value = "/survivor-stats/{type}", method = RequestMethod.GET)
   public SingleDataSeriesResponse getSurvivorStats(@PathVariable("type")
   String type)
   {
       log.debug("getSurvivorStats({})", type);

       final QSurvivorDetail qSurvivorDetail0 = new QSurvivorDetail("s1");

       final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);

       final List<Tuple> infectedSurvivors = jpaQueryFactory
           .select(qSurvivorDetail0.survivor().id, Wildcard.countAsInt).from(qSurvivorDetail0)
           .where(qSurvivorDetail0.infected.isTrue())
           .groupBy(qSurvivorDetail0.survivor().id).having(qSurvivorDetail0.survivor().id.count().goe(3))
           .fetch();

       log.debug("infectedSurvivors: {}", infectedSurvivors.size());
       long totalItemCount = jpaQueryFactory.selectFrom(qSurvivor).fetchCount();
       log.debug("totalItemCount: {}", totalItemCount);

      String fieldName = "";
      if ("status".equalsIgnoreCase(type) || "survivor_status".equalsIgnoreCase(type)) {
         fieldName = " infected ";
      }
      else {
         fieldName = " infected ";
      }

      SingleDataSeriesResponse response = new SingleDataSeriesResponse();
      final List<SingleSeries> dataItemList = Arrays.asList(
          new SingleSeries("infected", new BigDecimal(infectedSurvivors.size())),
          new SingleSeries("not infected", new BigDecimal(totalItemCount - infectedSurvivors.size()))
      );

      response.setItems(dataItemList);
      response.setOperationStatus(ResponseStatusEnum.SUCCESS);
      response.setOperationMessage("Survivors by " + fieldName);
      return response;
   }

}
