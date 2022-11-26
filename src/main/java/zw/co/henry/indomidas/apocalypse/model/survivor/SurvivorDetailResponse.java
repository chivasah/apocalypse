package zw.co.henry.indomidas.apocalypse.model.survivor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.model.response.PageResponse;

import java.util.List;

/**
 * @author henry
 */
@Data
@EqualsAndHashCode(callSuper = false)
final public class SurvivorDetailResponse extends PageResponse
{
   @ApiModelProperty(required = true, value = "")
   private List<SurvivorDTO> items;
}
