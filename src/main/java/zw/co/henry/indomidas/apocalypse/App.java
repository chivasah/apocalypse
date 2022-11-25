package zw.co.henry.indomidas.apocalypse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"zw.co.henry.indomidas.apocalypse.repo"})
@EntityScan(basePackages = { "zw.co.henry.indomidas.apocalypse.model" })
@EnableTransactionManagement
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
