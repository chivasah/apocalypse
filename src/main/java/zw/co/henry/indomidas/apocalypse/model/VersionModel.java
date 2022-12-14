package zw.co.henry.indomidas.apocalypse.model;

import lombok.Data;

@Data // For Getters and Setters
public class VersionModel
{
   private String version = null;
   private Integer major = null;
   private Integer minor = null;
   private Integer patch = null;
}
