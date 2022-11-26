package zw.co.henry.indomidas.apocalypse.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;

/**
 * @author henry
 */
@Data
@EqualsAndHashCode(callSuper = false)
final public class UserResponse extends OperationResponse
{
   private User data = new User();
}
