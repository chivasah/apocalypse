package zw.co.henry.indomidas.apocalypse;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"zw.co.henry.indomidas.apocalypse.repo"})
@EntityScan(basePackages = { "zw.co.henry.indomidas.apocalypse.model" })
@EnableTransactionManagement
public class App
{
   public static void main(String[] args) throws Exception
   {
      new SpringApplication(App.class).run(args);
   }

   public static void browse(String url)
   {
      if (Desktop.isDesktopSupported()) {
         Desktop desktop = Desktop.getDesktop();
         try {
            desktop.browse(new URI(url));
         }
         catch (IOException | URISyntaxException e) {
            e.printStackTrace();
         }
      }
      else {
         Runtime runtime = Runtime.getRuntime();
         try {
            final String operatingSystemName = System.getProperty("os.name").toLowerCase();
            if (operatingSystemName.contains("nix") || operatingSystemName.contains("nux")) {
               runtime.exec("xdg-open " + url);
            }
            else if (operatingSystemName.contains("win")) {
               runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            }
            else if (operatingSystemName.contains("mac")) {
               runtime.exec("open " + url);
            }
            else {
               System.out.println("an unknown operating system!!");
            }
         }
         catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   @EventListener({ ApplicationReadyEvent.class })
   public void applicationReadyEvent()
   {
      System.out.println("try to launch browser");
      browse("localhost:8181/#");
   }
}
