package zw.co.henry.indomidas.apocalypse.api.robot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import zw.co.henry.indomidas.apocalypse.model.response.SingleDataSeriesResponse;
import zw.co.henry.indomidas.apocalypse.service.RobotService;

/**
 * @author henry
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Robot"})
public class RobotStatsController {

    @Autowired
    private RobotService robotService;

    @ApiOperation(value = "Robot Stats", response = SingleDataSeriesResponse.class)
    @RequestMapping(value = "/robot-stats/{type}", method = RequestMethod.GET)
    public SingleDataSeriesResponse getRobotStats(@PathVariable("type")
                                                          String type) {
        return robotService.getRobotStatsForType(type);
    }
}
