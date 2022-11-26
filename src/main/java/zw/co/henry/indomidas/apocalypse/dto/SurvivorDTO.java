package zw.co.henry.indomidas.apocalypse.dto;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

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
final public class SurvivorDTO implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Nullable
   private Integer id;

   @NotNull
   private String name;

   @NotNull
   private Integer age;

   @NotNull
   private String gender;

   @Nullable
   private String lastLocation;

   @Nullable
   private List<SurvivorDetailDTO> survivorDetails;

   @Nullable
   private List<SurvivorInventoryDTO> inventory;

   private Boolean infected;
}
