package zw.co.henry.indomidas.apocalypse.service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import zw.co.henry.indomidas.apocalypse.dto.RobotDTO;
import zw.co.henry.indomidas.apocalypse.model.response.SingleDataSeriesResponse;
import zw.co.henry.indomidas.apocalypse.model.robot.RobotResponse;

import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * @author henry
 */
public interface RobotService {
    List<RobotDTO> connectToRobotCPUSystem() throws IOException;

    List<RobotDTO> fetchRobotList();

    @Transactional(readOnly = true)
    SingleDataSeriesResponse getRobotStatsForType(@NotNull String type$);

    @Transactional(readOnly = true)
    RobotResponse getListOfRobotsByPage(@Nullable String category$, Pageable pageable$);
}
