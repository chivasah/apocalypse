package zw.co.henry.indomidas.apocalypse.model.survivor;

import java.util.List;

import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.model.response.PageResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurvivorResponse extends PageResponse
{
   @ApiModelProperty(required = true, value = "")
   private List<SurvivorDTO> items;
}