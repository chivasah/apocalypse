package zw.co.henry.indomidas.apocalypse.api.survivor;

import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.dto.SurvivorDetailDTO;
import zw.co.henry.indomidas.apocalypse.dto.converters.SurvivorDTOFromSurvivorConverter;
import zw.co.henry.indomidas.apocalypse.dto.converters.SurvivorDetailDTOFromSurvivorDetailConverter;
import zw.co.henry.indomidas.apocalypse.dto.converters.SurvivorFromSurvivorDTOConverter;
import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;
import zw.co.henry.indomidas.apocalypse.model.survivor.Survivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorDetail;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorDetailResponse;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorResponse;
import zw.co.henry.indomidas.apocalypse.repo.SurvivorRepo;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@Api(tags = { "Survivors" })
@Slf4j
public class SurvivorController
{
   private final SurvivorDetailDTOFromSurvivorDetailConverter dtoFromSurvivorDetailConverter = new SurvivorDetailDTOFromSurvivorDetailConverter();
   private final SurvivorDTOFromSurvivorConverter dtoFromSurvivorConverter = new SurvivorDTOFromSurvivorConverter();
   private final SurvivorFromSurvivorDTOConverter survivorFromDTOConverter = new SurvivorFromSurvivorDTOConverter();

   @PersistenceContext(unitName = "default")
   private EntityManager entityManager;

   @Autowired
   private SurvivorRepo survivorRepo;

//   @ApiOperation(value = "List of survivors", response = SurvivorResponse.class)
   @RequestMapping(value = "/survivors", method = RequestMethod.GET)
   public SurvivorResponse getSurvivorsByPage(
//           @ApiParam(value = "")
           @RequestParam(value = "page", defaultValue = "0", required = false)
   Integer page,
//           @ApiParam(value = "between 1 to 1000")
           @RequestParam(value = "size", defaultValue = "20", required = false)
   Integer size, @RequestParam(value = "survivorId", required = false)
   Integer survivorId, @RequestParam(value = "category", required = false)
   String category, Pageable pageable)
   {
      SurvivorResponse resp = new SurvivorResponse();
      Survivor qry = new Survivor();
      if (survivorId != null) {
         qry.setId(survivorId);
      }
      //        if (category  != null)  { qry.setCategory(category); }

      Page<Survivor> survivorPage = survivorRepo.findAll(org.springframework.data.domain.Example.of(qry), pageable);
      resp.setPageStats(survivorPage, true);
      resp.setItems(survivorPage.getContent().stream().map(dtoFromSurvivorConverter::convert).collect(Collectors.toList()));
      return resp;
   }

//   @ApiOperation(value = "Add new survivor", response = OperationResponse.class)
   @RequestMapping(value = "/survivors", method = RequestMethod.POST, produces = { "application/json" })
   public OperationResponse addNewSurvivor(@RequestBody
   SurvivorDTO survivorDTO, HttpServletRequest req)
   {

      OperationResponse resp = new OperationResponse();
      Survivor qry = new Survivor();
      qry.setId(survivorDTO.getId());

      if (this.survivorRepo.exists( org.springframework.data.domain.Example.of(qry))) {
         resp.setOperationStatus(ResponseStatusEnum.ERROR);
         resp.setOperationMessage("Unable to add Survivor - Survivor already exist ");
      }
      else {
        Survivor survivor = survivorFromDTOConverter.convert(survivorDTO);
          SurvivorDetail details = new SurvivorDetail();
          details.setSurvivor(survivor);
          details.setInfected(false);
          if (survivor != null) {
              survivor.setSurvivorDetails(Collections.singletonList(details));
          }
          log.info("{}", survivor);
        this.survivorRepo.save(survivor);
         resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
         resp.setOperationMessage("Survivor Registered");
      }
      return resp;
   }

//   @ApiOperation(value = "Flag survivor as infected", response = OperationResponse.class)
   @RequestMapping(value = "/survivors/{survivorId}", method = RequestMethod.DELETE, produces = { "application/json" })
   public OperationResponse flagSurvivorAsInfected(@PathVariable("survivorId")
   Integer survivorId, HttpServletRequest req)
   {
      OperationResponse resp = new OperationResponse();
       Survivor qry = new Survivor();
       qry.setId(survivorId);

       if (this.survivorRepo.exists( org.springframework.data.domain.Example.of(qry))) {
         Survivor survivor = this.survivorRepo.findOneById(survivorId).get();
         SurvivorDetail detail = new SurvivorDetail();
         detail.setSurvivor(survivor);
         detail.setInfected(true);
         survivor.getSurvivorDetails().add(detail);
         this.survivorRepo.saveAndFlush(survivor);
         resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
         resp.setOperationMessage("Survivor flagged as infected");
      }
      else {
         resp.setOperationStatus(ResponseStatusEnum.ERROR);
         resp.setOperationMessage("No Survivor Exist");
      }
      return resp;
   }

//    @ApiOperation(value = "Update survivor location", response = OperationResponse.class)
    @RequestMapping(value = "/survivors/{survivorId}/location", method = RequestMethod.PUT, produces = { "application/json" })
    public OperationResponse updateSurvivorLocation(@PathVariable("survivorId")
                                                Integer survivorId, @RequestBody String location, HttpServletRequest req)
    {
        OperationResponse resp = new OperationResponse();
        Survivor qry = new Survivor();
        qry.setId(survivorId);

        if (this.survivorRepo.exists( org.springframework.data.domain.Example.of(qry))) {
            Survivor survivor = this.survivorRepo.findOneById(survivorId).get();
            survivor.setLastLocation(location);
            this.survivorRepo.saveAndFlush(survivor);
            resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Survivor location updated");
        }
        else {
            resp.setOperationStatus(ResponseStatusEnum.ERROR);
            resp.setOperationMessage("No Survivor Exist");
        }
        return resp;
    }

//    @ApiOperation(value = "Survivor Details", response = SurvivorDetailResponse.class)
   @RequestMapping(value = "/survivor-details", method = RequestMethod.GET)
   public SurvivorDetailResponse getSurvivorDetail(@RequestParam(value = "survivorid", required = false)
   Integer survivorId)
   {
      long prevSurvivorId = -1, newSurvivorId;
      Survivor qry = new Survivor();
      if (survivorId != null) {
         qry.setId(survivorId);
      }
      long itemCount = survivorRepo.count(org.springframework.data.domain.Example.of(qry));
      Optional<Survivor> result = survivorRepo.findOneById(survivorId);
      //        log.info("result: {}", result);
      SurvivorDetailResponse resp = new SurvivorDetailResponse();
      result.ifPresent(survivor$ -> resp.setItems(Collections.singletonList(dtoFromSurvivorConverter.convert(survivor$))));

      resp.setPageTotal((int) itemCount, true);
      return resp;
   }

}
