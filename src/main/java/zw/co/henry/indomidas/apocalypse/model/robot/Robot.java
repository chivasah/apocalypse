package zw.co.henry.indomidas.apocalypse.model.robot;

import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "robots", schema = "john")
@DynamicUpdate
public class Robot
{
   @Id
   @Column(name = "serial_number")
   private String serialNumber;

   private String model;

   @Column(name = "manufactured_date")
   private Timestamp manufacturedDate;
   //    @Enumerated(EnumType.STRING)
   //    @Convert(converter = RoleConverter.class)
   private String category;
}
