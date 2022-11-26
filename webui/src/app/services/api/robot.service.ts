import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';
import { HttpParams} from '@angular/common/http';

@Injectable()
export class RobotService {

    constructor(
        private apiRequest: ApiRequestService,
        private translate: TranslateService
    ) {}

    /**
     * Gets List of robots
     */
    getRobots(page?: number, size?: number): Observable<any> {
        console.log("getRobots()");

        const me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === 'number' ? page.toString() : '0');
        params = params.append('size', typeof size === 'number' ? size.toString() : '1000');
        const robotListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/robots', params)
            .subscribe(jsonResp => {
                const returnObj = jsonResp.items.map(function(v, i, a) {
                    const newRow = Object.assign({}, v, {
                        robotDate  : me.translate.getDateString(v.robotDate),
                        // paidDate   : me.translate.getDateString(v.paidDate),
                        // shippedDate: me.translate.getDateString(v.shippedDate)
                    });
                    return newRow;
                });
                robotListSubject.next(returnObj);
            });
        return robotListSubject;
    }

    /**
     * Gets Robots and Robot Lines (Products in each robot)
     */
    getRobotDetails(robotId: number): Observable<any> {
        const me = this;
        let params: HttpParams = new HttpParams();
        if (robotId) {
            params = params.append('robotid', robotId.toString());
        }
        const robotDetailSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/robot-details', params)
            .subscribe(jsonResp => {
                const returnObj = jsonResp.items.map(function(v, i, a) {
                    const newRow = Object.assign({}, v, {
                        robotDate  : me.translate.getDateString(v.robotDate),
                        paidDate   : me.translate.getDateString(v.paidDate),
                        shippedDate: me.translate.getDateString(v.shippedDate)
                    });
                    return newRow;
                });
                robotDetailSubject.next(returnObj);
            });

        return robotDetailSubject;
    }

    getRobotStats(field: string): Observable<any> {
        return this.apiRequest.get('api/robot-stats/' + field );
    }
}
