package zw.co.henry.indomidas.apocalypse.dto.converters;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.dto.SurvivorDetailDTO;
import zw.co.henry.indomidas.apocalypse.dto.SurvivorInventoryDTO;
import zw.co.henry.indomidas.apocalypse.model.survivor.Survivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorInventory;

/**
 * @author henry
 */
public final class SurvivorInventoryDTOFromSurvivorInventoryConverter implements Converter<SurvivorInventory, SurvivorInventoryDTO>
{

   @Override
   public SurvivorInventoryDTO convert(final SurvivorInventory source)
   {
      if (isNull(source)) {
         return null;
      }

      SurvivorDTO survivorDTO = null;
      if(nonNull(source.getSurvivor())){
          survivorDTO = new SurvivorDTO();
          survivorDTO.setId(source.getId());
      }

      return new SurvivorInventoryDTO(source.getId(), survivorDTO, source.getCategory(), source.getQuantity());
   }
}
