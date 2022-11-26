package zw.co.henry.indomidas.apocalypse.service;

import org.springframework.transaction.annotation.Transactional;
import zw.co.henry.indomidas.apocalypse.model.response.SingleDataSeriesResponse;

import javax.validation.constraints.NotNull;

/**
 * @author henry
 */
public interface InventoryService {
    @Transactional(readOnly = true)
    SingleDataSeriesResponse getInventoryStatsForType(@NotNull String type$);
}
