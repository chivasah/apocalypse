package zw.co.henry.indomidas.apocalypse.model.user;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import zw.co.henry.indomidas.apocalypse.dto.converters.persistence.RoleConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user", schema = "john")
@DynamicUpdate
public class User
{
   @Id
   @Column(name = "user_id")
   @Getter
   @Setter
   private String userId;
   @Getter
   @Setter
   private String password = "";
   @Getter
   @Setter
   private String company;
   @Column(name = "first_name")
   @Getter
   @Setter
   private String firstName;
   @Column(name = "last_name")
   @Getter
   @Setter
   private String lastName;
   @Getter
   @Setter
   private String email;

   @Column(name = "security_provider_id")
   @JsonIgnore
   @Getter
   @Setter
   private int securityProviderId;
   @Column(name = "default_customer_id")
   @JsonIgnore
   @Getter
   @Setter
   private int defaultCustomerId;

   @JsonIgnore
   @Getter
   @Setter
   private String phone;
   @JsonIgnore
   @Getter
   @Setter
   private String address1;
   @JsonIgnore
   @Getter
   @Setter
   private String address2;
   @JsonIgnore
   @Getter
   @Setter
   private String country;
   @JsonIgnore
   @Getter
   @Setter
   private String postal;

   //    @Enumerated(EnumType.STRING)
   @Convert(converter = RoleConverter.class)
   @Getter
   @Setter
   private Role role;

   //@JsonIgnore
   @Column(name = "is_active")
   @JsonIgnore
   @Getter
   @Setter
   private boolean isActive;
   //@JsonIgnore
   @Column(name = "is_blocked")
   @JsonIgnore
   @Getter
   @Setter
   private boolean isBlocked;
   @Column(name = "secret_question")
   @JsonIgnore
   @Getter
   @Setter
   private String secretQuestion;
   @Column(name = "secret_answer")
   @JsonIgnore
   @Getter
   @Setter
   private String secretAnswer;
   @Column(name = "enable_beta_testing")
   @JsonIgnore
   @Getter
   @Setter
   private boolean enableBetaTesting;
   @Column(name = "enable_renewal")
   @JsonIgnore
   @Getter
   @Setter
   private boolean enableRenewal;

   public User() {
      this("new", "PASSWORD", Role.USER, "new", "new", true, "", "", "", "", "", "", "", "", true, false);
   }

   public User(String userId, String password, String firstName, String lastName) {
      this(userId, password, Role.USER, firstName, lastName, true, "", "", "", "", "", "", "", "", true, false);
   }

   public User(String userId, String password, Role role, String firstName, String lastName) {
      this(userId, password, role, firstName, lastName, true, "", "", "", "", "", "", "", "", true, false);
   }

   public User(String userId, String password, Role role, String firstName, String lastName, boolean isActive) {
      this(userId, password, role, firstName, lastName, isActive, "", "", "", "", "", "", "", "", true, false);
   }

   public User(String userId, String password, Role role, String firstName, String lastName, boolean isActive, String company, String phone, String address1,
         String address2, String country, String postal, String secretQuestion, String secretAnswer, boolean enableRenewal, boolean enableBetaTesting) {
      this.setUserId(userId);
      this.setEmail(userId);
//      this.setPassword(new BCryptPasswordEncoder().encode(password));
      this.setRole(role);
      this.setFirstName(firstName);
      this.setLastName(lastName);
      this.setActive(isActive);
      this.setCompany(company);
      this.setPhone(phone);
      this.setAddress1(address1);
      this.setAddress2(address2);
      this.setCountry(country);
      this.setPostal(postal);
      this.setSecretQuestion(secretQuestion);
      this.setSecretAnswer(secretAnswer);
      this.setEnableRenewal(enableRenewal);
      this.setEnableBetaTesting(enableBetaTesting);
   }

   public String getFullName()
   {
      return this.firstName + this.lastName;
   }
}
