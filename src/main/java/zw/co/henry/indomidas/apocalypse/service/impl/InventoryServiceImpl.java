package zw.co.henry.indomidas.apocalypse.service.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import zw.co.henry.indomidas.apocalypse.model.data.SingleSeries;
import zw.co.henry.indomidas.apocalypse.model.response.SingleDataSeriesResponse;
import zw.co.henry.indomidas.apocalypse.model.survivor.QSurvivorInventory;
import zw.co.henry.indomidas.apocalypse.service.InventoryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum.SUCCESS;

/**
 * @author henry
 */
@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    private final QSurvivorInventory qSurvivorInventory = QSurvivorInventory.survivorInventory;

    @Override
    public SingleDataSeriesResponse getInventoryStatsForType(final String type$) {
        log.debug("getInventoryStatsForType({})", type$);

        NumberExpression<Integer> value;
        if ("quantity".equalsIgnoreCase(type$)) {
            value = qSurvivorInventory.quantity.sum().as("value");
        } else {
            value = Wildcard.countAsInt.as("value");
        }

        final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
        final List<Tuple> result = jpaQueryFactory.select(value
                        , qSurvivorInventory.category.as("name"))
                .from(qSurvivorInventory)
                .groupBy(qSurvivorInventory.category).fetch();
        final List<SingleSeries> dataItemList = result.stream().
                map(o -> {
                            final Optional<Integer> optionalValue = Optional.ofNullable(o.get(0, Integer.class));
                            if (optionalValue.isPresent()) {
                                final Integer value0 = optionalValue.get();
                                return new SingleSeries(o.get(1, String.class), new BigDecimal(value0));
                            } else {
                                return null;
                            }
                        }
                )
                .collect(Collectors.toList());

        SingleDataSeriesResponse response = new SingleDataSeriesResponse();
        response.setItems(dataItemList);
        response.setOperationStatus(SUCCESS);
        response.setOperationMessage("Inventory by " + type$);
        return response;
    }
}
