import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { TranslateService } from './translate.service';
import { ApiRequestService } from './api-request.service';
import { HttpParams} from '@angular/common/http';

@Injectable()
export class SurvivorService {
    constructor(
        private apiRequest: ApiRequestService,
        private translate: TranslateService
    ) {}

    /**
     * Gets List of survivors
     */
    getSurvivorInfo(page?: number, size?: number): Observable<any> {

        const me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === 'number' ? page.toString() : '0');
        params = params.append('size', typeof size === 'number' ? size.toString() : '1000');
        const survivorListSubject = new Subject<any>();
        this.apiRequest.get('api/survivors', params)
            .subscribe(jsonResp => {
                const returnObj = jsonResp.items.map(function(v, i, a) {
                    const newRow = Object.assign({}, v, {
                        survivorDate  : me.translate.getDateString(v.survivorDate),
                        // paidDate   : me.translate.getDateString(v.paidDate),
                        // shippedDate: me.translate.getDateString(v.shippedDate)
                    });
                    return newRow;
                });
                survivorListSubject.next(returnObj);
            });
        return survivorListSubject;
    }

    /**
     * Gets Survivors and Survivor Lines (Products in each survivor)
     */
    getSurvivorDetails(survivorId: number): Observable<any> {

        const me = this;
        let params: HttpParams = new HttpParams();
        if (survivorId) {
            params = params.append('survivorid', survivorId.toString());
        }
        const survivorDetailSubject = new Subject<any>();
        this.apiRequest.get('api/survivor-details', params)
            .subscribe(jsonResp => {
                const returnObj = jsonResp.items.map(function(v, i, a) {
                    const newRow = Object.assign({}, v, {
                        // survivorDate  : me.translate.getDateString(v.survivorDate),
                        // paidDate   : me.translate.getDateString(v.paidDate),
                        // shippedDate: me.translate.getDateString(v.shippedDate)
                    });
                    return newRow;
                });
                survivorDetailSubject.next(returnObj);
            });

        return survivorDetailSubject;
    }


    getSurvivorStats(field: string): Observable<any> {
        return this.apiRequest.get(`api/survivor-stats/${field}` );
    }

    registerSurvivor(survivor: {'name': string, 'age': number, 'gender': string}): Observable<any> {
        return this.apiRequest.post('api/survivors', survivor );
    }

    flagSurvivorAsInfected(survivorId: number): Observable<any> {
        const me = this;
        const survivorDetailSubject = new Subject<any>();
        this.apiRequest.post(`api/survivors/flag-survivor-as-infected`, survivorId)
            .subscribe(jsonResp => survivorDetailSubject.next(jsonResp));
        return survivorDetailSubject;
    }

    updateSurvivorLocation(survivor: {id: number, lastLocation: string}): Observable<any> {
        console.log('updateSurvivorLocation:', survivor);
        const me = this;
        const survivorDetailSubject = new Subject<any>();
        this.apiRequest.patch(`api/survivors/${survivor.id}/location`, survivor)
            .subscribe(jsonResp => {
                survivorDetailSubject.next(jsonResp);
            });

        return survivorDetailSubject;
    }
}
