package zw.co.henry.indomidas.apocalypse.dto.converters;

import static java.util.Objects.isNull;

import org.springframework.core.convert.converter.Converter;

import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.dto.SurvivorDetailDTO;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorDetail;

import java.util.Collections;
import java.util.Objects;

/**
 * @author henry
 */
public final class SurvivorDetailDTOFromSurvivorDetailConverter implements Converter<SurvivorDetail, SurvivorDetailDTO>
{

   @Override
   public SurvivorDetailDTO convert(final SurvivorDetail source)
   {
      if (isNull(source)) {
         return null;
      }

      SurvivorDTO survivorDTO = null;
            if(Objects.nonNull(source.getSurvivor())){
                source.getSurvivor().setSurvivorDetails(Collections.emptyList());
                final SurvivorDTOFromSurvivorConverter fromSurvivorConverter = new SurvivorDTOFromSurvivorConverter();
                survivorDTO = fromSurvivorConverter.convert(source.getSurvivor());
            }

      return new SurvivorDetailDTO(source.getId(), survivorDTO, source.getInfected());
   }
}
