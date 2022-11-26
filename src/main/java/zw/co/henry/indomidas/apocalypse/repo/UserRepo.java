package zw.co.henry.indomidas.apocalypse.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zw.co.henry.indomidas.apocalypse.model.user.User;

/**
 * @author henry
 */
public interface UserRepo extends JpaRepository<User, Long>
{
   Optional<User> findOneByUserId(String userId);

   Optional<User> findOneByUserIdAndPassword(String userId, String password);
}
