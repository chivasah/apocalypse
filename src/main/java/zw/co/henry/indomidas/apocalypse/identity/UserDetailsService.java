package zw.co.henry.indomidas.apocalypse.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import zw.co.henry.indomidas.apocalypse.model.user.User;
import zw.co.henry.indomidas.apocalypse.repo.UserRepo;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService
{

   private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
   @Autowired
   private UserRepo userRepo;

   @Override
   public final TokenUser loadUserByUsername(String username) throws UsernameNotFoundException, DisabledException
   {

      final User user = userRepo.findOneByUserId(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
      TokenUser currentUser;
      if (user.isActive()) {
         currentUser = new TokenUser(user);
      }
      else {
         throw new DisabledException("User is not activated (Disabled User)");
      }
      detailsChecker.check(currentUser);
      return currentUser;
   }
}
