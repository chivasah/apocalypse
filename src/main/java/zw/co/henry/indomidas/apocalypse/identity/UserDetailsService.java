package zw.co.henry.indomidas.apocalypse.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;


import zw.co.henry.indomidas.apocalypse.model.user.User;
import zw.co.henry.indomidas.apocalypse.repo.UserRepo;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public final TokenUser loadUserByUsername(String username) throws UsernameNotFoundException, DisabledException {

        final User user = userRepo.findOneByUserId(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        TokenUser currentUser;
        if (user.isActive() == true){
            currentUser = new TokenUser(user);
        }
        else{
            throw new DisabledException("User is not activated (Disabled User)");
            //If pending activation return a disabled user
            //currentUser = new TokenUser(user, false);
        }
        detailsChecker.check(currentUser);
        return currentUser;
    }
}
