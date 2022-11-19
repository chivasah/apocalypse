package zw.co.henry.indomidas.apocalypse.dto.converters;

import static java.util.Objects.isNull;

import org.springframework.core.convert.converter.Converter;

import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.dto.SurvivorDetailDTO;
import zw.co.henry.indomidas.apocalypse.model.survivor.Survivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorDetail;

import java.util.Objects;

/**
 * @author henry
 */
public final class SurvivorDetailFromSurvivorDetailDTOConverter implements Converter<SurvivorDetailDTO, SurvivorDetail>
{

   @Override
   public SurvivorDetail convert(final SurvivorDetailDTO source)
   {
      if (isNull(source)) {
         return null;
      }

      Survivor survivor = null;
            if(Objects.nonNull(source.getSurvivor())){
                final SurvivorFromSurvivorDTOConverter fromSurvivorDTOConverter = new SurvivorFromSurvivorDTOConverter();
                survivor = fromSurvivorDTOConverter.convert(source.getSurvivor());
            }

      return new SurvivorDetail(source.getId(), survivor, source.getInfected());
   }
}
