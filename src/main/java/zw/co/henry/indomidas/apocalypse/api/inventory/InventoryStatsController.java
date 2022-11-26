package zw.co.henry.indomidas.apocalypse.api.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import zw.co.henry.indomidas.apocalypse.model.response.SingleDataSeriesResponse;
import zw.co.henry.indomidas.apocalypse.service.InventoryService;

/**
 * @author henry
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Inventory"})
@Slf4j
public class InventoryStatsController {

    @Autowired
    private InventoryService inventoryService;

    @ApiOperation(value = "Inventory Stats", response = SingleDataSeriesResponse.class)
    @RequestMapping(value = "/inventory-stats/{type}", method = RequestMethod.GET)
    public SingleDataSeriesResponse getInventoryStats(@PathVariable("type")
                                                              String type) {
        log.debug("getInventoryStats({})", type);

        return inventoryService.getInventoryStatsForType(type);
    }
}
