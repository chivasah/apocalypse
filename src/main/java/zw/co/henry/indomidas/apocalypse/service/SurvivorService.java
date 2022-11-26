package zw.co.henry.indomidas.apocalypse.service;


import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import zw.co.henry.indomidas.apocalypse.dto.SurvivorDTO;
import zw.co.henry.indomidas.apocalypse.model.response.NumericResponse;
import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;
import zw.co.henry.indomidas.apocalypse.model.survivor.SurvivorResponse;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * @author henry
 */
public interface SurvivorService {

    @Transactional(readOnly = true)
    SurvivorResponse getListOfNonInfectedSurvivorsByPage(@Nullable String category$, Pageable pageable$);

    @Transactional(readOnly = true)
    SurvivorResponse getListOfInfectedSurvivorsByPage(@Nullable String category$, Pageable pageable$);

    @Transactional(readOnly = true)
    NumericResponse getPercentageOfInfectedSurvivors(@Nullable String category$, Pageable pageable$);

    @Transactional(readOnly = true)
    NumericResponse getPercentageOfNonInfectedSurvivors(@Nullable String category$, Pageable pageable$);

    @Transactional
    OperationResponse updateSurvivorLocation(@NotNull SurvivorDTO survivorDTO$);
}
