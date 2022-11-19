package zw.co.henry.indomidas.apocalypse.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author henry
 */
@Slf4j
@ApiIgnore
@Controller
public class MainController
{

   @RequestMapping(value = { "/" })
   public String index()
   {
      return "index.html";
   }

}
