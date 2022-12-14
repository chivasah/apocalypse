package zw.co.henry.indomidas.apocalypse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author henry
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
final public class SurvivorDetailDTO implements Serializable
{
   private static final long serialVersionUID = 1L;

//   @javax.annotation.Nullable
   private Integer id;

   private SurvivorDTO survivor;

   private Boolean infected;
}
