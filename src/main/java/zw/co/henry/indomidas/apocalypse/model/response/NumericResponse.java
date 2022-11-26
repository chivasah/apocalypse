package zw.co.henry.indomidas.apocalypse.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author henry
 */
@Data
@EqualsAndHashCode(callSuper = false)
final public class NumericResponse extends OperationResponse
{
   @ApiModelProperty(required = true, value = "")
   private Number result;
}
