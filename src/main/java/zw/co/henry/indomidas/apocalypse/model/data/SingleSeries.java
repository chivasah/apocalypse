package zw.co.henry.indomidas.apocalypse.model.data;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author henry
 */
@Value
public class SingleSeries
{
   String name;
   BigDecimal value;
}
