import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { SurvivorService } from '../../services/api/survivor.service';
import { Router } from '@angular/router';
import 'rxjs/add/operator/mergeMap';


@Component({
    selector: 'app-survivors-list-pg',
    templateUrl: './survivors.component.html',
    styleUrls: [ './survivors.scss'],
})

export class SurvivorsComponent implements OnInit {
    @ViewChild('survivorIdTpl') survivorIdTpl: TemplateRef<any>;
    columns: any[];
    rows: any[];
    survivorByStatusData: any[] = [];
    isLoading = false;
    constructor(private router: Router, private survivorService: SurvivorService) { }

    ngOnInit() {
        const me = this;
        me.getPageData();
        this.columns = [
            {prop: 'id'         , name: 'ID'           , width: 65, cellTemplate: this.survivorIdTpl   },
            {prop: 'name'    , name: 'Name'         , width: 150 },
            {prop: 'age'   , name: 'Age'        , width: 80 },
            {prop: 'gender' , name: 'Gender'      , width: 110 },
            {prop: 'lastLocation'     , name: 'Last Location'     , width: 200  },
            {prop: 'infected'     , name: 'Infected'     , width: 200  },
        ];
    }

    getPageData() {
        const me = this;
        const legendColors = {'infected': '#ef2e2e', 'non infected': '#61c673'};
        me.isLoading = true;
        me.survivorService.getSurvivorStats('status')
            .mergeMap(function(statusData) {
                console.log(statusData);
                me.survivorByStatusData = statusData.items.map(function(v, i, a) {
                    return {name: v.name, value: v.value, color: legendColors[v.name]};
                });
                console.log('Got Survivor Stats');
                return me.survivorService.getSurvivorInfo();
            })
            .subscribe(function(survivorData) {
                me.rows = survivorData;
                me.isLoading = false;
                console.log('Got Survivor Data');
            });
    }
}
