package zw.co.henry.indomidas.apocalypse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

/**
 * @author henry
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
final public class SurvivorInventoryDTO implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Nullable
   private Integer id;

   private SurvivorDTO survivor;

   private String category;

   private Integer quantity;
}
