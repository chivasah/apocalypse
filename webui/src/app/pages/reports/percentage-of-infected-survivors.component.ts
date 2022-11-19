import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ReportsService} from '../../services/api/reports.service';

@Component({
    selector: 'app-percentage-of-infected-survivors-pg',
    template: `
        <div class="pad-16">
            <h3>Percentage Of Infected Survivors</h3>
            <div class="s-info-bar">
                <div style="flex:1"></div>
                <div *ngIf="isLoading" class="spinner spinner-md" style="margin:0 10px;">Loading...</div>
                <h1 *ngIf="!isLoading">{{value}}%</h1>
            </div>
        </div>
    `,
    styles: [`
        h5 {
            margin-bottom: 5px;
        }
    `]
})
export class PercentageOfInfectedSurvivorsComponent implements OnInit {
    value: number;
    isLoading: boolean = false;

    constructor(private router: Router, private reportsService: ReportsService) {
    }

    ngOnInit() {
        const me = this;
        me.getPageData();
    }

    getPageData() {

        const me = this;
        me.isLoading = true;
        me.reportsService.getPercentageOfInfectedSurvivors().subscribe((pageData) => {
            console.log(pageData);
            this.value = Math.round(100 * pageData.result);
            me.isLoading = false;
        });
    }
}
