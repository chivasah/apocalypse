package zw.co.henry.indomidas.apocalypse.api.robot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import zw.co.henry.indomidas.apocalypse.model.robot.RobotResponse;
import zw.co.henry.indomidas.apocalypse.service.RobotService;

/**
 * @author henry
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Robot"})
@Slf4j
public class RobotController {

    @Autowired
    private RobotService robotService;

    @ApiOperation(value = "List of robots", response = RobotResponse.class)
    @RequestMapping(value = "/robots", method = RequestMethod.GET)
    public RobotResponse getRobotsByPage(
            @ApiParam(value = "")
            @RequestParam(value = "page", defaultValue = "0", required = false)
                    Integer page,
            @ApiParam(value = "between 1 to 1000")
            @RequestParam(value = "size", defaultValue = "20", required = false)
                    Integer size, @RequestParam(value = "category", required = false)
                    String robotCategory, Pageable pageable) {
        log.debug("getRobotsByPage({})", robotCategory);

        return robotService.getListOfRobotsByPage(robotCategory, pageable);
    }
}
