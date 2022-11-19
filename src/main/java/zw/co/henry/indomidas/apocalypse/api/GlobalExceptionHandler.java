package zw.co.henry.indomidas.apocalypse.api;

import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;

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
      OperationResponse resp = new OperationResponse();
      resp.setOperationStatus(ResponseStatusEnum.ERROR);
      resp.setOperationMessage(e.getRootCause().getMessage());
      return resp;
   }
}
