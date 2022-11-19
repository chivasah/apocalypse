package zw.co.henry.indomidas.apocalypse.dto.converters;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import org.springframework.core.convert.converter.Converter;

import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.dto.SurvivorInventoryDTO;
import zw.co.henry.indomidas.apocalypse.model.survivor.Survivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorInventory;

/**
 * @author henry
 */
public final class SurvivorInventoryFromSurvivorInventoryDTOConverter implements Converter<SurvivorInventoryDTO, SurvivorInventory>
{

   @Override
   public SurvivorInventory convert(final SurvivorInventoryDTO source)
   {
      if (isNull(source)) {
         return null;
      }

      Survivor survivor = null;
      if(nonNull(source.getSurvivor())){
          survivor = new Survivor();
          survivor.setId(source.getSurvivor().getId());
          survivor.setName(source.getSurvivor().getName());
          survivor.setAge(source.getSurvivor().getAge());
          survivor.setGender(source.getSurvivor().getGender());
          survivor.setLastLocation(source.getSurvivor().getLastLocation());
      }

      return new SurvivorInventory(source.getId(), survivor, source.getCategory(), source.getQuantity());
   }
}
