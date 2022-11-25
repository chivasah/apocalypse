package zw.co.henry.indomidas.apocalypse.dto;

import java.io.Serializable;

import lombok.ToString;
import lombok.Value;

//import javax.annotation.Nonnull;

/**
 * @author henry
 */
@Value
@ToString
public class ContactDTO implements Serializable
{
   private static final long serialVersionUID = 1L;

//   @javax.annotation.Nullable
   Integer id;
   String email;
   String phone;
}
