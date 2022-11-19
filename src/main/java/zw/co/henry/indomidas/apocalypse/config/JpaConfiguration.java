package zw.co.henry.indomidas.apocalypse.config;

import static java.util.Objects.nonNull;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author henry
 */
@Configuration
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
//@EnableJpaRepositories(basePackages = {"zw.co.henry.indomidas.apocalypse.model"})
public class JpaConfiguration
{
   @Value("${spring.jpa.database-platform}")
   private String hibernateDialect;

   @Value("${spring.jpa.properties.hibernate.show_sql:false}")
   private boolean showSql;

   private DataSource dataSource;

   @Autowired
   public void setDataSource(final DataSource dataSource)
   {
      this.dataSource = dataSource;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory()
   {
      LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
      factoryBean.setDataSource(dataSource);
      factoryBean.setPackagesToScan("zw.co.henry.indomidas.apocalypse.model");
      factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
      factoryBean.setJpaProperties(jpaProperties());
      factoryBean.setPersistenceUnitName("default");
      //      factoryBean.setMappingResources("META-INF/orm.xml");
      return factoryBean;
   }

   @Primary
   @Bean(name = "entityManager")
   public EntityManager entityManager(@Qualifier("entityManagerFactory")
   EntityManagerFactory entityManagerFactory)
   {
      return entityManagerFactory.createEntityManager();
   }

   @Bean
   public JpaVendorAdapter jpaVendorAdapter()
   {
      return new HibernateJpaVendorAdapter();
   }

   private Properties jpaProperties()
   {
      Properties properties = new Properties();
      if (nonNull(hibernateDialect) && !hibernateDialect.isEmpty()) {
         properties.put("hibernate.dialect", hibernateDialect);
      }
      return properties;
   }

   @Bean
   public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
   {
      JpaTransactionManager txManager = new JpaTransactionManager();
      txManager.setEntityManagerFactory(emf);
      return txManager;
   }

   @Bean
   public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager)
   {
      return new TransactionTemplate(transactionManager);
   }

}
