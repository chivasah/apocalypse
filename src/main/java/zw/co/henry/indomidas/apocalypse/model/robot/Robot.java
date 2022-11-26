package zw.co.henry.indomidas.apocalypse.model.robot;

import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "robots", schema = "john")
@DynamicUpdate
public class Robot
{
   @Id
   @Column(name = "serial_number", length = 32)
   private String serialNumber;

   @Column(length = 32, nullable = false)
   @NotEmpty
   private String model;

   @Column(name = "manufactured_date", nullable = false)
   @NotNull
   private Timestamp manufacturedDate;

   @Column(length = 50, nullable = false)
   @NotEmpty
   private String category;
}
