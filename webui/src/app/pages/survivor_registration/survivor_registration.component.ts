import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FormBuilder, FormGroup , FormControl, Validators} from '@angular/forms';
import 'rxjs/add/operator/switchMap';
import {SurvivorService} from '../../services/api/survivor.service';
import {OperationResponse} from "../../model/OperationResponse";

@Component({
	selector: 'app-survivors-registration',
	templateUrl: './survivor_registration.component.html',
    styleUrls: [ './survivor_registration.scss'],
})

export class SurvivorRegistrationComponent implements OnInit {
    public survivorId: number;
    public survivorDetailsRec: any = {id: '', survivorLine: []};
    isLoading: boolean = false;

    public rows = [];
    public columns = [
        {prop: 'productName' , name: 'Product'    , width: 200 },
        {prop: 'productCode' , name: 'Code'       , width: 70  },
        {prop: 'category'    , name: 'Category'   , width: 100 },
        {prop: 'listPrice'   , name: 'List Price' , width: 70  }
    ];
    genders: Array<string> = ['Male', 'Female'];

    public fg: FormGroup = this.formBuilder.group({
        id: [0],
        name: ['', [Validators.required, Validators.minLength(3)]],
        age: [0, [Validators.required
            , Validators.minLength(2)
            , Validators.maxLength(2)
            , Validators.pattern(/^\d{2}$/)
            // , Validators.pattern(/^-?(0|[1-9]\d*)?$/)
        ]],
        gender   : ['Male', Validators.required],
    });

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private readonly formBuilder: FormBuilder,
        private survivorService: SurvivorService
    ) {

    }

    ngOnInit(): void {
    }

    onNgSubmit() {
        const me = this;
        if (!me.fg.valid) {
            return;
        }
        console.log(me.fg.getRawValue());
        me.isLoading = true;
        const survivor = me.fg.getRawValue();
        me.survivorService.registerSurvivor(survivor)
            .subscribe((resp: OperationResponse) => {
                console.log('Survivor details', resp);
                setTimeout(() => window.alert(resp.operationMessage), 0);
                setTimeout(() => me.isLoading = false, 0);
                me.router.navigate(['/home/survivors/list']);
            }, error => {
                console.log(error.error.message);
            });
    }
}
