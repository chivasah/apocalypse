package zw.co.henry.indomidas.apocalypse.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author henry
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NumericResponse extends OperationResponse
{
   private Number result;
}
