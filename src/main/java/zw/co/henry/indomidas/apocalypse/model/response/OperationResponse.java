/**
 * This is the common structure for all responses
 * if the response contains a list(array) then it will have 'items' field
 * if the response contains a single item then it will have 'item'  field
 */

package zw.co.henry.indomidas.apocalypse.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author henry
 */
@Data
public class OperationResponse {
    @ApiModelProperty(required = true)
    private ResponseStatusEnum operationStatus;
    private String operationMessage;

    public enum ResponseStatusEnum {
        SUCCESS,
        WARNING,
        ERROR,
        NO_ACCESS
    }
}
