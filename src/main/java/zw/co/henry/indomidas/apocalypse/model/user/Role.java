package zw.co.henry.indomidas.apocalypse.model.user;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role implements Serializable {
                                          USER,
                                          ADMIN
}
