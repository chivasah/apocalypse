package zw.co.henry.indomidas.apocalypse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.annotation.Nullable;

/**
 * @author henry
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
final public class RobotDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //    {"model":"10QSI","serialNumber":"IFU3KA88FM5FQ8F","manufacturedDate":"2023-01-01T02:42:12.3101279+00:00","category":"Flying"}

    @Nullable
    private String serialNumber;

    @Nullable
    private String model;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'+00:00'")
//     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ")
    private Timestamp manufacturedDate;

    @Nullable
    private String category;
}
