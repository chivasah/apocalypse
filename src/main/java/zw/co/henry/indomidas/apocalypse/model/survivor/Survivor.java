package zw.co.henry.indomidas.apocalypse.model.survivor;

import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "survivorDetails", "inventory" })
@Entity
@Table(name = "survivors", schema = "john")
@DynamicUpdate
@Slf4j
public class Survivor
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   //    private String survivorStatus;

   @Column(nullable = false, length = 25)
   @NotNull
   private String name;

   @Column(nullable = false)
   @NotNull
   private Integer age;

   @NotNull
   @Column(columnDefinition = "CHAR(6) DEFAULT 'Male'", length = 6, nullable = false)
   private String gender;

   @Column(name = "last_location", columnDefinition = "VARCHAR(25) DEFAULT '-17.829167,31.052222'", length = 25)
   private String lastLocation;

   //    @OneToMany(mappedBy = "survivor")
   ////@OrderBy("customerName")
   //    private List<SurvivorDetail> survivorDetails;

   //   @LazyCollection(LazyCollectionOption.FALSE)
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "survivor", fetch = FetchType.EAGER)
   //   @BatchSize(size=100)
   //   @ToString.Exclude
   @OrderBy("id desc")
   private List<SurvivorDetail> survivorDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "survivor")
    @OrderBy("category")
    private List<SurvivorInventory> inventory;

   @Transient
   private Boolean infected;

   @PostLoad
   private void onPostLoad()
   {
      log.trace("onPostLoad({})", this.id);
      long infectedCount = Objects.isNull(this.survivorDetails) ? 0 : this.survivorDetails.stream().mapToInt(o -> o.getInfected() ? 1 : 0).count();
      log.trace("infectedCount: {}", infectedCount);
      infected = infectedCount >= 3;
      log.trace("infected: {}", infected);
   }

   @Override
   public boolean equals(final Object o$)
   {
      if (this == o$) {
         return true;
      }
      if (o$ == null || Hibernate.getClass(this) != Hibernate.getClass(o$)) {
         return false;
      }
      final Survivor survivor = (Survivor) o$;
      return id != null && Objects.equals(id, survivor.id);
   }

   @Override
   public int hashCode()
   {
      return getClass().hashCode();
   }
}
