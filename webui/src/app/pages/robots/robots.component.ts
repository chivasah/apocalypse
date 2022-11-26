import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { RobotService } from '../../services/api/robot.service';
import { Router } from '@angular/router';

@Component({
	selector: 'app-robots-pg',
	templateUrl: './robots.component.html',
    styleUrls: [ './robots.scss'],
})

export class RobotsComponent implements OnInit {
    columns: any[];
    rows: any[];

    constructor( private router: Router, private robotService: RobotService) {}
    ngOnInit() {
        const me = this;
        me.getPageData();
        this.columns = [
            {prop: 'serialNumber'  , name: 'Serial Number'         , width: 200 },
            {prop: 'model'  , name: 'Model'         , width: 120  },
            {prop: 'manufacturedDate'        , name: 'Manufactured'     , width: 300 },
            {prop: 'category'     , name: 'Category'     , width: 120 }
        ];

    }

    getPageData() {

        this.robotService.getRobots().subscribe( (pageData) => {
            console.log(pageData);
            this.rows = pageData;
        });
    }


}
