package zw.co.henry.indomidas.apocalypse.api;

import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import zw.co.henry.indomidas.apocalypse.model.session.SessionItem;
import zw.co.henry.indomidas.apocalypse.model.session.SessionResponse;
import zw.co.henry.indomidas.apocalypse.model.user.Login;
import zw.co.henry.indomidas.apocalypse.model.user.User;
import zw.co.henry.indomidas.apocalypse.repo.UserRepo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/*
This is a dummy rest controller, for the purpose of documentation (/session) path is map to a filter
 - This will only be invoked if security is disabled
 - If Security is enabled then SessionFilter.java is invoked
 - Enabling and Disabling Security is done at config/applicaton.properties 'security.ignored=/**'
*/

@RestController
@Api(tags = { "Authentication" })
public class SessionController
{

   @Autowired
   private UserRepo userRepo;

   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Will return a security token, which must be passed in every request", response = SessionResponse.class) })
   @RequestMapping(value = "/session", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public SessionResponse newSession(@RequestBody
   Login login, HttpServletRequest request, HttpServletResponse response)
   {
      User user = userRepo.findOneByUserIdAndPassword(login.getUsername(), login.getPassword()).orElse(null);
      SessionResponse resp = new SessionResponse();
      SessionItem sessionItem = new SessionItem();
      if (user != null) {
         sessionItem.setToken("xxx.xxx.xxx");
         sessionItem.setUserId(user.getUserId());
         sessionItem.setFirstName(user.getFirstName());
         sessionItem.setLastName(user.getLastName());
         sessionItem.setEmail(user.getEmail());
         //sessionItem.setRole(user.getRole());

         resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
         resp.setOperationMessage("Dummy Login Success");
         resp.setItem(sessionItem);
      }
      else {
         resp.setOperationStatus(ResponseStatusEnum.ERROR);
         resp.setOperationMessage("Login Failed");
      }
      return resp;
   }

}
