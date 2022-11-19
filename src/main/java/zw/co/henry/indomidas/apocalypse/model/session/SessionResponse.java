package zw.co.henry.indomidas.apocalypse.model.session;

import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SessionResponse extends OperationResponse
{
   @ApiModelProperty(required = true, value = "")
   private SessionItem item;
}
