import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';
import { HttpParams} from '@angular/common/http';

@Injectable()
export class InventoryService {

    constructor(
        private apiRequest: ApiRequestService,
        private translate: TranslateService
    ) {}

    getInventoryStats(field: string): Observable<any> {
        return this.apiRequest.get('api/inventory-stats/' + field );
    }


}
