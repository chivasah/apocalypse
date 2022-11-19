package zw.co.henry.indomidas.apocalypse.model.robot;

import java.util.List;

import zw.co.henry.indomidas.apocalypse.dto.RobotDTO;
import zw.co.henry.indomidas.apocalypse.model.response.PageResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RobotResponse extends PageResponse
{
   @ApiModelProperty(required = true, value = "")
   private List<RobotDTO> items;
}
