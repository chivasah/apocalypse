package zw.co.henry.indomidas.apocalypse.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurvivorDTO implements Serializable
{
   private static final long serialVersionUID = 1L;

   @javax.annotation.Nullable
   private Integer id;

   @NotNull
   private String name;

   @NotNull
   private Integer age;

   @NotNull
   private String gender;

   @javax.annotation.Nullable
   private String lastLocation;

   @javax.annotation.Nullable
   private List<SurvivorDetailDTO> survivorDetails;

   @javax.annotation.Nullable
   private List<SurvivorInventoryDTO> inventory;

   private Boolean infected;
}
