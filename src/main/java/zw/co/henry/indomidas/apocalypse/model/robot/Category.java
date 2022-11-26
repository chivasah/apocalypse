package zw.co.henry.indomidas.apocalypse.model.robot;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * @author henry
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category implements Serializable {
                                              FLYING,
                                              LAND
}
