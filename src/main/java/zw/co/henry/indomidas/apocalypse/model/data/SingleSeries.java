package zw.co.henry.indomidas.apocalypse.model.data;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author henry
 */
@Data
public class SingleSeries
{
   private String name;
   private BigDecimal value;

   public SingleSeries(String name, BigDecimal value) {
      this.name = name;
      this.value = value;
   }
}
