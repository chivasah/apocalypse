package zw.co.henry.indomidas.apocalypse.model.robot;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category implements Serializable {
                                              FLYING,
                                              LAND
}
