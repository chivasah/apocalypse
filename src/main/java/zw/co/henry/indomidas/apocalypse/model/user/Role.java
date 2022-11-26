package zw.co.henry.indomidas.apocalypse.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role implements Serializable {
                                          USER,
                                          ADMIN
}
