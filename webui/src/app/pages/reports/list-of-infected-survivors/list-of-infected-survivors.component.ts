import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import {ReportsService} from '../../../services/api/reports.service';

@Component({
	selector: 'app-list-of-infected-survivors-pg',
	templateUrl: './list-of-infected-survivors.component.html',
    styleUrls: [ './list-of-infected-survivors.scss'],
})

export class ListOfInfectedSurvivorsComponent implements OnInit {


    columns: any[];
    rows: any[];


    constructor( private router: Router, private reportsService: ReportsService) {}
    ngOnInit() {
        const me = this;
        me.getPageData();
        this.columns = [
            {prop: 'name'  , name: 'Name'         , width: 200 },
            {prop: 'age'  , name: 'Age'         , width: 120  },
            {prop: 'gender'  , name: 'Gender'         , width: 120  },
            {prop: 'infected'  , name: 'infected'         , width: 120  },
        ];

    }

    getPageData() {

        this.reportsService.getListOfInfectedSurvivors().subscribe( (pageData) => {
            console.log(pageData);
            this.rows = pageData.items;
        });
    }


}
