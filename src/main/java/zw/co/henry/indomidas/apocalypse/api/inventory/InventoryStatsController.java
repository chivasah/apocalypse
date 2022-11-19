package zw.co.henry.indomidas.apocalypse.api.inventory;

import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import zw.co.henry.indomidas.apocalypse.model.data.SingleSeries;
import zw.co.henry.indomidas.apocalypse.model.response.SingleDataSeriesResponse;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Inventory" })
public class InventoryStatsController
{

   @Autowired
   private JdbcTemplate jdbcTemplate;

   @ApiOperation(value = "Inventory Stats", response = SingleDataSeriesResponse.class)
   @RequestMapping(value = "/inventory-stats/{type}", method = RequestMethod.GET)
   public SingleDataSeriesResponse getInventoryStats(@PathVariable("type")
   String type)
   {
      String fieldName = "";
      if ("quantity".equalsIgnoreCase(type)) {
         fieldName = " sum(quantity) ";
      }
      else {
         fieldName = " count(*) ";
      }

      String sql = "select "+fieldName+" as value, category as name from SurvivorInventory group by category";
      String countType = "";
      long count;
      SingleSeries singleSerise;
      SingleDataSeriesResponse resp = new SingleDataSeriesResponse();
      ArrayList<SingleSeries> dataItemList = new ArrayList<SingleSeries>();

      List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

      for (Map<String, Object> row : list) {
         singleSerise = new SingleSeries((String) row.get("name"), new BigDecimal((long) row.get("value")));
         dataItemList.add(singleSerise);
      }
      resp.setItems(dataItemList);
      resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
      resp.setOperationMessage("Inventory by " + type);
      //resp.setItems(singleSerise);
      return resp;
   }

}
