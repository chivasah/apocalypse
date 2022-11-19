import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FormBuilder, FormGroup , Validators} from '@angular/forms';
import 'rxjs/add/operator/switchMap';
import {SurvivorService} from '../../services/api/survivor.service';

import first from 'lodash-es/first';
import {OperationResponse} from '../../model/OperationResponse';

@Component({
	selector: 'app-survivors-location',
	templateUrl: './survivor_location.component.html',
    styleUrls: [ './survivor_location.scss'],
})

export class SurvivorLocationComponent implements OnInit {
    public survivorId: number;
    public survivorDetailsRec: any = {id: '', survivorLine: []};
    genders: Array<string> = ['Male', 'Female'];

    public fg: FormGroup = this.formBuilder.group({
        id: [0, Validators.required],
        name: ['' ],
        age: [0],
        gender   : [''],
        infected   : [''],
        lastLocation   : ['unknown', [Validators.required, Validators.minLength(5), Validators.pattern(/^-?([1-8]?[1-9]|[1-9]0)\.{1}\d{1,6}/)]],
        survivorDetails: ['']
    });

    isLoading = false;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private readonly formBuilder: FormBuilder,
        private survivorService: SurvivorService
    ) {
        setTimeout(() => this.getData(), 0);
    }

    ngOnInit(): void {
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
                me.fg.setValue({
                    id: [first(resp).id],
                    name   : [first(resp).name],
                    age   : [first(resp).age],
                    gender   : [first(resp).gender],
                    infected   : [first(resp).infected],
                    lastLocation   : [first(resp).lastLocation],
                    survivorDetails: [first(resp).survivorDetails]
                });
                me.survivorDetailsRec = first(resp);
                me.isLoading = false;
            });
    }

    onNgSubmit(): void {
        const me = this;
        if (!me.fg.valid) {
            return;
        }

        me.isLoading = true;
        console.log(me.fg.getRawValue());

        const survivor = me.fg.getRawValue();
        const newLocation = survivor.lastLocation;
        me.survivorService.updateSurvivorLocation(me.survivorId, newLocation)
            .subscribe((resp: OperationResponse) => {
                console.log('Survivor details', resp);
                window.alert(resp.operationMessage);
                me.isLoading = false;
            }, error => {
                console.log(error.error.message);
                me.isLoading = false;
            });
    }
}
