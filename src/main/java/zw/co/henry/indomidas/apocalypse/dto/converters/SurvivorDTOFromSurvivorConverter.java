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

/**
 * @author henry
 */
public final class SurvivorDTOFromSurvivorConverter implements Converter<Survivor, SurvivorDTO>
{

   @Override
   public SurvivorDTO convert(final Survivor source)
   {
      if (isNull(source)) {
         return null;
      }

      List<SurvivorDetailDTO> result = new ArrayList<>();
      if (Objects.nonNull(source.getSurvivorDetails()) && !source.getSurvivorDetails().isEmpty()) {
         final SurvivorDetailDTOFromSurvivorDetailConverter fromSurvivorDetailConverter = new SurvivorDetailDTOFromSurvivorDetailConverter();

         result = source.getSurvivorDetails().stream()
             .map(o -> {
                 o.setSurvivor(null);
                 return o;
             })
             .map(fromSurvivorDetailConverter::convert)
             .collect(Collectors.toList());
      }

      List<SurvivorInventoryDTO> inventory = new ArrayList<>();
      if (nonNull(source.getInventory())) {
         final SurvivorInventoryDTOFromSurvivorInventoryConverter dtoFromSurvivorInventoryConverter = new SurvivorInventoryDTOFromSurvivorInventoryConverter();
         inventory = source.getInventory().stream()
             .map(o -> {
                 o.setSurvivor(null);
                 return o;
             })
             .map(dtoFromSurvivorInventoryConverter::convert).collect(Collectors.toList());
      }

      return new SurvivorDTO(source.getId(), source.getName(), source.getAge(), source.getGender(), source.getLastLocation(), result, inventory,
                             source.getInfected());
   }
}
