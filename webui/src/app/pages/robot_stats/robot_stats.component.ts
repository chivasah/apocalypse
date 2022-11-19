import {Component, OnInit, OnDestroy} from '@angular/core';
import {RobotService} from '../../services/api/robot.service';
import {Router} from '@angular/router';
import 'rxjs/add/operator/mergeMap';


@Component({
    selector: 'app-robot-stats-pg',
    templateUrl: './robot_stats.component.html',
    styleUrls: ['./robot_stats.scss'],
})

export class RobotStatsComponent implements OnInit, OnDestroy {
    view: any[] = [460, 180];
    robotsByCategoryData: any[] = [];
    robotsByPaymentData: any[] = [];
    robotsByCountryData: any[] = [];
    colorScheme = {
        domain: ['#61c673', '#ff8e28', '#ef2e2e', '#007cbb']
    };
    barColorScheme = {
        domain: ['#007cbb']
    };

    private cri: any;

    constructor(private router: Router, private robotService: RobotService) {
    }

    ngOnInit() {
        const me = this;
        me.getPageData();
        this.cri = setInterval(() => {
            me.getPageData();
        }, 60000);
    }

    public ngOnDestroy(): void {
        clearInterval(this.cri);
        delete this.cri;
    }

    getPageData() {
        const me = this;

        /**
         * This is an Example of sequencing RxJS observable using mergeMap
         * (We are sequencing the API calls as the H2 DB used by the backend is failing to serve multiple request at once)
         */
        me.robotService.getRobotStats('category')
            .mergeMap(function (statusData) {
                me.robotsByCategoryData = statusData.items;
                console.log('Received Robots By Category');
                return me.robotService.getRobotStats('paytype');
            }).subscribe(function (countryData) {
            me.robotsByCountryData = countryData.items;
            console.log('Received Robots By Country');
        });
    }


}
