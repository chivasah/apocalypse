package zw.co.henry.indomidas.apocalypse.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import zw.co.henry.indomidas.apocalypse.model.survivor.Survivor;

/**
 * @author henry
 */
@Transactional
public interface SurvivorRepo extends JpaRepository<Survivor, Integer>
{
   Optional<Survivor> findOneById(Integer id);
}
