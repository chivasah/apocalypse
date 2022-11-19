package zw.co.henry.indomidas.apocalypse.dto.converters;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.model.survivor.Survivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorDetail;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorInventory;

/**
 * @author henry
 */
public final class SurvivorFromSurvivorDTOConverter implements Converter<SurvivorDTO, Survivor>
{
   @Override
   public Survivor convert(final SurvivorDTO source)
   {
      if (isNull(source)) {
         return null;
      }

      List<SurvivorDetail> result = new ArrayList<>();
      if (Objects.nonNull(source.getSurvivorDetails()) && !source.getSurvivorDetails().isEmpty()) {
         final SurvivorDetailFromSurvivorDetailDTOConverter survivorDetailFromDTO = new SurvivorDetailFromSurvivorDetailDTOConverter();

         result = source.getSurvivorDetails().stream().map(survivorDetailFromDTO::convert).collect(Collectors.toList());
      }

       List<SurvivorInventory> inventory = new ArrayList<>();
      if (nonNull(source.getInventory())) {
         final SurvivorInventoryFromSurvivorInventoryDTOConverter fromSurvivorInventoryDTO = new SurvivorInventoryFromSurvivorInventoryDTOConverter();
         inventory = source.getInventory().stream().map(fromSurvivorInventoryDTO::convert).collect(Collectors.toList());
      }

      return new Survivor(source.getId(), source.getName(), source.getAge(), source.getGender(), source.getLastLocation(), result, inventory, null);
   }
}
