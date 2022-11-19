package zw.co.henry.indomidas.apocalypse.dto.converters;

import static java.util.Objects.isNull;

import org.springframework.core.convert.converter.Converter;

import zw.co.henry.indomidas.apocalypse.dto.RobotDTO;
import zw.co.henry.indomidas.apocalypse.model.robot.Robot;

/**
 * @author henry
 */
public final class RobotDTOFromRobotConverter implements Converter<Robot, RobotDTO>
{

   @Override
   public RobotDTO convert(final Robot source)
   {
      if (isNull(source)) {
         return null;
      }

      return new RobotDTO(source.getSerialNumber(), source.getModel(), source.getManufacturedDate(), source.getCategory());
   }
}
