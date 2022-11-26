package zw.co.henry.indomidas.apocalypse.api.survivor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.dto.converters.SurvivorDTOFromSurvivorConverter;
import zw.co.henry.indomidas.apocalypse.dto.converters.SurvivorFromSurvivorDTOConverter;
import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;
import zw.co.henry.indomidas.apocalypse.model.survivor.Survivor;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorDetail;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorDetailResponse;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorResponse;
import zw.co.henry.indomidas.apocalypse.repo.SurvivorRepo;
import zw.co.henry.indomidas.apocalypse.service.SurvivorService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum;

/**
 * @author henry
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Survivors"})
@Slf4j
public class SurvivorController {
    private final SurvivorDTOFromSurvivorConverter dtoFromSurvivorConverter = new SurvivorDTOFromSurvivorConverter();
    private final SurvivorFromSurvivorDTOConverter survivorFromDTOConverter = new SurvivorFromSurvivorDTOConverter();

    @Autowired
    private SurvivorRepo survivorRepo;

    @Autowired
    private SurvivorService survivorService;

    @ApiOperation(value = "List of survivors", response = SurvivorResponse.class)
    @RequestMapping(value = "/survivors", method = RequestMethod.GET)
    public SurvivorResponse getSurvivorsByPage(
            @ApiParam(value = "")
            @RequestParam(value = "page", defaultValue = "0", required = false)
                    Integer page,
            @ApiParam(value = "between 1 to 1000")
            @RequestParam(value = "size", defaultValue = "20", required = false)
                    Integer size, @RequestParam(value = "survivorId", required = false)
                    Integer survivorId, @RequestParam(value = "category", required = false)
                    String category, Pageable pageable) {
        SurvivorResponse response = new SurvivorResponse();
        Survivor qry = new Survivor();
        if (survivorId != null) {
            qry.setId(survivorId);
        }

        Page<Survivor> survivorPage = survivorRepo.findAll(Example.of(qry), pageable);
        response.setPageStats(survivorPage, true);
        response.setItems(survivorPage.getContent().stream().map(dtoFromSurvivorConverter::convert).collect(Collectors.toList()));
        return response;
    }

    @ApiOperation(value = "Add new survivor", response = OperationResponse.class)
    @RequestMapping(value = "/survivors", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse addNewSurvivor(@RequestBody
                                                    SurvivorDTO survivorDTO, HttpServletRequest req) {

        OperationResponse response = new OperationResponse();
        Survivor qry = new Survivor();
        qry.setId(survivorDTO.getId());

        if (this.survivorRepo.exists(Example.of(qry))) {
            response.setOperationStatus(ResponseStatusEnum.ERROR);
            response.setOperationMessage("Unable to add Survivor - Survivor already exist ");
        } else {
            Survivor survivor = survivorFromDTOConverter.convert(survivorDTO);
            SurvivorDetail details = new SurvivorDetail();
            details.setSurvivor(survivor);
            details.setFromDate(Timestamp.valueOf(LocalDateTime.now()));
            details.setInfected(false);
            if (survivor != null) {
                survivor.setSurvivorDetails(Collections.singletonList(details));
            }
            log.info("{}", survivor);
            this.survivorRepo.save(survivor);
            response.setOperationStatus(ResponseStatusEnum.SUCCESS);
            response.setOperationMessage("Survivor Registered");
        }
        return response;
    }

    @ApiOperation(value = "Flag survivor as infected", response = OperationResponse.class)
    @RequestMapping(value = "/survivors/flag-survivor-as-infected", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse flagSurvivorAsInfected(@RequestBody
                                                            Integer survivorId, HttpServletRequest req) {
        final OperationResponse response = new OperationResponse();
        Survivor qry = new Survivor();
        if (survivorId != null) {
            qry.setId(survivorId);
        }

        if (this.survivorRepo.exists(Example.of(qry))) {
            Survivor survivor = this.survivorRepo.findOneById(survivorId).get();
            SurvivorDetail detail = new SurvivorDetail();
            detail.setSurvivor(survivor);
            detail.setFromDate(Timestamp.valueOf(LocalDateTime.now()));
            detail.setInfected(true);
            survivor.getSurvivorDetails().add(detail);
            this.survivorRepo.saveAndFlush(survivor);
            response.setOperationStatus(ResponseStatusEnum.SUCCESS);
            response.setOperationMessage("Survivor flagged as infected");
        } else {
            response.setOperationStatus(ResponseStatusEnum.ERROR);
            response.setOperationMessage("No Survivor Exist");
        }
        return response;
    }

    @ApiOperation(value = "Update survivor location", response = OperationResponse.class)
    @RequestMapping(value = "/survivors/{survivorId}/location", method = RequestMethod.PATCH, produces = {"application/json"})
    public OperationResponse updateSurvivorLocation(@RequestBody
                                                            SurvivorDTO survivorDTO, HttpServletRequest req) {
        log.debug("updateSurvivorLocation({})", survivorDTO);

        return survivorService.updateSurvivorLocation(survivorDTO);
    }

    @ApiOperation(value = "Survivor Details", response = SurvivorDetailResponse.class)
    @RequestMapping(value = "/survivor-details", method = RequestMethod.GET)
    public SurvivorDetailResponse getSurvivorDetail(@RequestParam(value = "survivorid", required = false)
                                                            Integer survivorId) {
        long prevSurvivorId = -1, newSurvivorId;
        Survivor qry = new Survivor();
        if (survivorId != null) {
            qry.setId(survivorId);
        }
        long itemCount = survivorRepo.count(Example.of(qry));
        Optional<Survivor> result = survivorRepo.findOneById(survivorId);
        //        log.info("result: {}", result);
        SurvivorDetailResponse response = new SurvivorDetailResponse();
        result.ifPresent(survivor$ -> response.setItems(Collections.singletonList(dtoFromSurvivorConverter.convert(survivor$))));

        response.setPageTotal((int) itemCount, true);
        return response;
    }

}
