import { OperationStatus } from './OperationStatus';

export interface OperationResponse {
    operationStatus: OperationStatus;
    operationMessage: string;
}
