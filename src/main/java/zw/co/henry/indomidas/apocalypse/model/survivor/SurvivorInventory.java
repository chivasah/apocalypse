package zw.co.henry.indomidas.apocalypse.model.survivor;

import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "john")
@DynamicUpdate
public class SurvivorInventory
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @ManyToOne(optional = false)
   @JoinColumn(name = "survivor_id")
   private Survivor survivor;

    @NotNull
   private String category;

   @NotNull
   private Integer quantity;
}
