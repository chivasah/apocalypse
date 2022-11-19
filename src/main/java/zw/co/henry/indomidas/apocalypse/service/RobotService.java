package zw.co.henry.indomidas.apocalypse.service;

import zw.co.henry.indomidas.apocalypse.dto.RobotDTO;

import java.io.IOException;
import java.util.List;

/**
 * @author henry
 */
public interface RobotService
{
   List<RobotDTO> connectToRobotCPUSystem() throws IOException;

   List<RobotDTO> fetchRobotList();
}
