package zw.co.henry.indomidas.apocalypse.api;

import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;

import static java.util.Objects.nonNull;

/**
 * @author henry
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler
{
   @ExceptionHandler(value = DataIntegrityViolationException.class)
   public OperationResponse handleBaseException(DataIntegrityViolationException e)
   {
      OperationResponse response = new OperationResponse();
      response.setOperationStatus(ResponseStatusEnum.ERROR);
      if(nonNull(e.getRootCause())) {
         response.setOperationMessage(e.getRootCause().getMessage());
      }
      return response;
   }
}
