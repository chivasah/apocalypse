package zw.co.henry.indomidas.apocalypse.model.user;

import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponse extends OperationResponse
{
   private User data = new User();
}
