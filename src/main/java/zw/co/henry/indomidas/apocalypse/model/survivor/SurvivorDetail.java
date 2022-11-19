/**
 * SurvivorDetails contains extra details of the survivor
 * Such as Customer Info (Name, Company , Email)
 * and List of Products in each survivor
 */

package zw.co.henry.indomidas.apocalypse.model.survivor;

import java.util.Objects;

import javax.persistence.*;

import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "survivor_details", schema = "john")
@DynamicUpdate
public class SurvivorDetail
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   //    @ManyToOne
   //    @JoinColumn(name = "user_id")
   //    private User user;
   ////
   //        @Column(name = "survivor_id")
   //    private Integer survivorId;

   @ManyToOne(optional = false)
   @JoinColumn(name = "survivor_id")
   private Survivor survivor;

   @Column(name = "infected")
   private Boolean infected;

   @Override
   public boolean equals(final Object o$)
   {
      if (this == o$) {
          return true;
      }
      if (o$ == null || Hibernate.getClass(this) != Hibernate.getClass(o$)) {
          return false;
      }
      final SurvivorDetail that = (SurvivorDetail) o$;
      return id != null && Objects.equals(id, that.id);
   }

   @Override
   public int hashCode()
   {
      return getClass().hashCode();
   }
}
