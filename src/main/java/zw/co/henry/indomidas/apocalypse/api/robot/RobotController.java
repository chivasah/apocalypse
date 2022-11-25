package zw.co.henry.indomidas.apocalypse.api.robot;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import zw.co.henry.indomidas.apocalypse.dto.converters.RobotDTOFromRobotConverter;
import zw.co.henry.indomidas.apocalypse.model.robot.Robot;
import zw.co.henry.indomidas.apocalypse.model.robot.RobotResponse;
import zw.co.henry.indomidas.apocalypse.repo.RobotRepo;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@Api(tags = { "Robot" })
public class RobotController
{

   private final RobotDTOFromRobotConverter dtoFromRobotConverter = new RobotDTOFromRobotConverter();
   @Autowired
   private RobotRepo robotRepo;

//   @ApiOperation(value = "List of robots", response = RobotResponse.class)
   @RequestMapping(value = "/robots", method = RequestMethod.GET)
   public RobotResponse getRobotsByPage(
//           @ApiParam(value = "")
           @RequestParam(value = "page", defaultValue = "0", required = false)
   Integer page,
//           @ApiParam(value = "between 1 to 1000")
           @RequestParam(value = "size", defaultValue = "20", required = false)
   Integer size, @RequestParam(value = "category", required = false)
   String robotCategory, Pageable pageable)
   {
      RobotResponse resp = new RobotResponse();
      Robot qry = new Robot();
      if (robotCategory != null) {
         qry.setCategory(robotCategory);
      }

      Page<Robot> pg = robotRepo.findAll(org.springframework.data.domain.Example.of(qry), pageable);
      resp.setPageStats(pg, true);
      resp.setItems(pg.getContent().stream().map(dtoFromRobotConverter::convert).filter(Objects::nonNull).collect(Collectors.toList()));
      return resp;
   }
}
