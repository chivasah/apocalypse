import { Component, ViewEncapsulation, ViewChild, OnInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';

import { LoginService   } from './services/api/login.service';
import { UserInfoService} from './services/user-info.service';

import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/switchMap';

@Component({
  selector   : 'home-comp',
  templateUrl: './home.component.html',
  styleUrls  : ['./home.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent   {

    public showAppAlert = false;
    public appHeaderItems = [
        {
            label   : 'Dashboard',
            href    : '/home/dashboard',
            subNav  : [
                { label: 'Robot Stats'  , href: '/home/dashboard/robot'  },
                { label: 'Inventory Stats'  , href: '/home/dashboard/inventory'  },
            ]
        },
        {
            label   : 'Survivors',
            href    : '/home/survivors',
            subNav  : [
                { label: 'List'  , href: '/home/survivors/list'  },
                { label: 'Register'  , href: '/home/survivors/register'  },
                { label: 'Map', href: '/home/survivors/map'}
            ]
        },
        {
            label   : 'Reports',
            href    : '/home/reports',
            subNav  : [
                { label: 'Percentage of infected survivors'  , href: '/home/reports/percentage-of-infected-survivors'  },
                { label: 'Percentage of non-infected survivors'  , href: '/home/reports/percentage-of-non-infected-survivors'  },
                { label: 'List of infected survivors'  , href: '/home/reports/list-of-infected-survivors'  },
                { label: 'List of non-infected survivors'  , href: '/home/reports/list-of-non-infected-survivors'  },
                { label: 'List of robots'  , href: '/home/reports/list-of-robots'  },
            ]
        },
    ];

    public selectedHeaderItemIndex = 0;
    public selectedSubNavItemIndex = 1;
    public userName = '';

    constructor(
        private router: Router,
        private activeRoute: ActivatedRoute,
        private loginService: LoginService,
        private userInfoService: UserInfoService
    ) {
        router.events
        .filter(event => event instanceof NavigationEnd)
        .map( _ => this.router.routerState.root)
        .map(route => {
            while (route.firstChild) { route = route.firstChild; }
            return route;
        })
        .mergeMap( route => route.data)
        .subscribe(data => {
            console.log('Route data===: ', data[0]);
            this.selectedHeaderItemIndex = data[0] ? data[0].selectedHeaderItemIndex : -1;
            this.selectedSubNavItemIndex = data[0] ? data[0].selectedSubNavItemIndex : -1;
        });
        this.userName = this.userInfoService.getUserName();

    }

    navbarSelectionChange(val) {
        // console.log(val);
    }

    closeAppAlert() {
        this.showAppAlert = false;
    }

}
