package zw.co.henry.indomidas.apocalypse.model.session;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;

/**
 * @author henry
 */
@Data
@EqualsAndHashCode(callSuper = false)
final public class SessionResponse extends OperationResponse
{
   @ApiModelProperty(required = true, value = "")
   private SessionItem item;
}
