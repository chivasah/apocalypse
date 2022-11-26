package zw.co.henry.indomidas.apocalypse.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zw.co.henry.indomidas.apocalypse.model.data.SingleSeries;

import java.util.List;

/**
 * @author henry
 */
@Data
@EqualsAndHashCode(callSuper = false)
final public class SingleDataSeriesResponse extends OperationResponse
{
   @ApiModelProperty(required = true, value = "")
   private List<SingleSeries> items;
}
