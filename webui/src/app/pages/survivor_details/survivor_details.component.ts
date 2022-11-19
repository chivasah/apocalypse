import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FormBuilder, FormGroup , FormControl, Validators} from '@angular/forms';
import 'rxjs/add/operator/switchMap';
import {SurvivorService} from '../../services/api/survivor.service';

import first from 'lodash-es/first';
import {OperationResponse} from '../../model/OperationResponse';

@Component({
	selector: 'app-survivors-details-pg',
	templateUrl: './survivor_details.component.html',
    styleUrls: [ './survivor_details.scss'],
})

export class SurvivorDetailsComponent implements OnInit {
    public survivorId: number;
    public survivorDetailsRec: any = {id: '', survivorLine: []};


    public frmSurvivorDetail: FormGroup = this.formBuilder.group({
name   : ['', Validators.required],
age   : ['', Validators.required],
gender   : ['', Validators.required],
infected   : [false, Validators.required],
lastLocation     : ['', Validators.required],
survivorDetails: new FormControl(['']),
        inventory: new FormControl([''])
    });

    isLoading: boolean = false;

    constructor(
        public readonly route: ActivatedRoute,
        private readonly router: Router,
        private formBuilder: FormBuilder,
        private survivorService: SurvivorService
    ) {
        // console.log( this.route.snapshot.params);
        setTimeout(() => this.getData(), 0);
    }

    ngOnInit(): void {
        this.getData();
    }

    getData(): void {
        const me = this;
        me.isLoading = true;
        this.route.params
        .switchMap( function(params: Params) {
            me.survivorId = params['id'];
            return me.survivorService.getSurvivorDetails(params['id']);
        })
        .subscribe(function(resp) {
            console.log('Survivor details', first(resp));
            me.frmSurvivorDetail.setValue({
                name   : [first(resp).name],
                age   : [first(resp).age],
                gender   : [first(resp).gender],
                infected   : [first(resp).infected],
                lastLocation   : [first(resp).lastLocation],
                survivorDetails: [first(resp).survivorDetails],
                inventory: [first(resp).inventory]
            });
            me.survivorDetailsRec = first(resp);

            setTimeout(() => me.isLoading = false, 0);
        });
    }

    onFlagSurvivorAsInfected($event: MouseEvent): void {
        const me  = this;
        me.isLoading = true;
        me.survivorService.flagSurvivorAsInfected(me.survivorId)
            .subscribe(function(resp: OperationResponse) {
                console.log('Survivor details', resp);
                setTimeout(() => window.alert(resp.operationMessage), 0);
                me.getData();
                setTimeout(() => me.isLoading = false, 0);
            });
    }
}
