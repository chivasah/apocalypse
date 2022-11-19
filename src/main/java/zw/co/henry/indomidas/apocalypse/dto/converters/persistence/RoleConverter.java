package zw.co.henry.indomidas.apocalypse.dto.converters.persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import zw.co.henry.indomidas.apocalypse.model.user.Role;

/**
 * @author henry
 */
@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String>
{

   @Override
   public String convertToDatabaseColumn(final Role attribute)
   {
      if (attribute == null) {
         return null;
      }

      switch (attribute) {
      case USER:
         return "USER";

      case ADMIN:
         return "ADMIN";

      default:
         throw new IllegalArgumentException(attribute + " not supported.");
      }
   }

   @Override
   public Role convertToEntityAttribute(final String dbData)
   {
      if (dbData == null) {
         return null;
      }

      switch (dbData) {
      case "USER":
         return Role.USER;

      case "ADMIN":
         return Role.ADMIN;
      default:
         throw new IllegalArgumentException(dbData + " not supported.");
      }
   }

}
