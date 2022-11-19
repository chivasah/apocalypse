package zw.co.henry.indomidas.apocalypse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import zw.co.henry.indomidas.apocalypse.model.robot.Robot;

/**
 * @author henry
 */
@Transactional
public interface RobotRepo extends JpaRepository<Robot, String>
{
}
