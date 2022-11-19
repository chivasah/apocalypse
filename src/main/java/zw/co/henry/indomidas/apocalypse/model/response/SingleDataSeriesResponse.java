package zw.co.henry.indomidas.apocalypse.model.response;

import java.util.List;

import zw.co.henry.indomidas.apocalypse.model.data.SingleSeries;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author henry
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SingleDataSeriesResponse extends OperationResponse
{
   private List<SingleSeries> items;
}
