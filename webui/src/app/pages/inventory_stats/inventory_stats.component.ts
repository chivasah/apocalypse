import {Component, OnInit, OnDestroy} from '@angular/core';
import {Router} from '@angular/router';
import 'rxjs/add/operator/mergeMap';
import {InventoryService} from '../../services/api/inventory.service';


@Component({
    selector: 'app-inventory-stats-pg',
    templateUrl: './inventory_stats.component.html',
    styleUrls: ['./inventory_stats.scss'],
})

export class InventoryStatsComponent implements OnInit, OnDestroy {
    view: any[] = [560, 180];
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
    ordersByCountryData: any;

    constructor(private router: Router, private inventoryService: InventoryService) {
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
        me.inventoryService.getInventoryStats('category')
            .mergeMap(function (statusData) {
                me.robotsByCategoryData = statusData.items;
                console.log('Received Robots By Category');
                return me.inventoryService.getInventoryStats('quantity');
            }).subscribe(function (countryData) {
            me.robotsByCountryData = countryData.items;
            console.log('Received Robots By Country');
        });
    }


}
