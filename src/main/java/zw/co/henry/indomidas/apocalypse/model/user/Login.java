package zw.co.henry.indomidas.apocalypse.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Login
{

   @ApiModelProperty(example = "demo", required = true)
   private String username = "";

   @ApiModelProperty(example = "demo", required = true)
   private String password = "";

}
