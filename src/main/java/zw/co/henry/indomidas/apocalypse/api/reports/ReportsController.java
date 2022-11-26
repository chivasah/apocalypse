package zw.co.henry.indomidas.apocalypse.api.reports;

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
import zw.co.henry.indomidas.apocalypse.model.response.NumericResponse;
import zw.co.henry.indomidas.apocalypse.model.robot.RobotResponse;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorResponse;
import zw.co.henry.indomidas.apocalypse.service.RobotService;
import zw.co.henry.indomidas.apocalypse.service.SurvivorService;

/**
 * @author henry
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Reports"})
@Slf4j
public class ReportsController {

    @Autowired
    private RobotService robotService;

    @Autowired
    private SurvivorService survivorService;

    @ApiOperation(value = "Percentage of infected survivors.", response = NumericResponse.class)
    @RequestMapping(value = "/percentage-of-infected-survivors", method = RequestMethod.GET)
    public NumericResponse getPercentageOfInfectedSurvivors(@RequestParam(value = "category", required = false)
                                                                    String category, Pageable pageable) {
        log.debug("getPercentageOfInfectedSurvivors({})", category);

        return survivorService.getPercentageOfInfectedSurvivors(category, pageable);
    }

    @ApiOperation(value = "Percentage of non-infected survivors.", response = NumericResponse.class)
    @RequestMapping(value = "/percentage-of-non-infected-survivors", method = RequestMethod.GET)
    public NumericResponse getPercentageOfNonInfectedSurvivors(@RequestParam(value = "category", required = false)
                                                                       String category, Pageable pageable) {
        log.debug("getPercentageOfInfectedSurvivors({})", category);

        return survivorService.getPercentageOfNonInfectedSurvivors(category, pageable);
    }

    @ApiOperation(value = "List of infected survivors", response = SurvivorResponse.class)
    @RequestMapping(value = "/list-of-infected-survivors", method = RequestMethod.GET)
    public SurvivorResponse getListOfInfectedSurvivorsByPage(
            @ApiParam(value = "")
            @RequestParam(value = "page", defaultValue = "0", required = false)
                    Integer page,
            @ApiParam(value = "between 1 to 1000")
            @RequestParam(value = "size", defaultValue = "20", required = false)
                    Integer size, @RequestParam(value = "category", required = false)
                    String category, Pageable pageable) {
        log.debug("getListOfInfectedSurvivorsByPage({})", category);

        return survivorService.getListOfInfectedSurvivorsByPage(category, pageable);
    }

    @ApiOperation(value = "List of non-infected survivors", response = SurvivorResponse.class)
    @RequestMapping(value = "/list-of-non-infected-survivors", method = RequestMethod.GET)
    public SurvivorResponse getListOfNonInfectedSurvivorsByPage(
            @ApiParam
            @RequestParam(value = "page", defaultValue = "0", required = false)
                    Integer page,
            @ApiParam(value = "between 1 to 1000")
            @RequestParam(value = "size", defaultValue = "20", required = false)
                    Integer size, @RequestParam(value = "category", required = false)
                    String category, Pageable pageable) {
        log.debug("getListOfNonInfectedSurvivorsByPage({})", category);

        return survivorService.getListOfNonInfectedSurvivorsByPage(category, pageable);
    }

    @ApiOperation(value = "List of robots", response = SurvivorResponse.class)
    @RequestMapping(value = "/list-of-robots", method = RequestMethod.GET)
    public RobotResponse getListOfRobotsByPage(
            @ApiParam
            @RequestParam(value = "page", defaultValue = "0", required = false)
                    Integer page,
            @ApiParam(value = "between 1 to 1000")
            @RequestParam(value = "size", defaultValue = "20", required = false)
                    Integer size, @RequestParam(value = "category", required = false)
                    String category, Pageable pageable) {
        log.debug("getListOfRobotsByPage({})", category);

        return robotService.getListOfRobotsByPage(category, pageable);
    }
}
