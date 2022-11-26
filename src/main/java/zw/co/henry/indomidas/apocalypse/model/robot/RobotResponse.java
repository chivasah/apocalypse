package zw.co.henry.indomidas.apocalypse.model.robot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zw.co.henry.indomidas.apocalypse.dto.RobotDTO;
import zw.co.henry.indomidas.apocalypse.model.response.PageResponse;

import java.util.List;

/**
 * @author henry
 */
@Data
@EqualsAndHashCode(callSuper = false)
final public class RobotResponse extends PageResponse
{
   @ApiModelProperty(required = true, value = "")
   private List<RobotDTO> items;
}
