import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent  }       from './home.component';

import { LoginComponent        }   from './pages/login/login.component';
import { LogoutComponent       }   from './pages/logout/logout.component';
import { DashboardComponent    }   from './pages/dashboard/dashboard.component';

import { AuthGuard } from './services/auth_guard.service';
import { PageNotFoundComponent }  from './pages/404/page-not-found.component';
import {SurvivorsComponent} from './pages/survivors/survivors.component';
import {SurvivorDetailsComponent} from './pages/survivor_details/survivor_details.component';
import {SurvivorRegistrationComponent} from './pages/survivor_registration/survivor_registration.component';
import {RobotStatsComponent} from './pages/robot_stats/robot_stats.component';
import {RobotsComponent} from './pages/robots/robots.component';
import {SurvivorLocationComponent} from './pages/survivor_location/survivor_location.component';
import {SurvivorInventoryComponent} from './pages/survivor_inventory/survivor_inventory.component';
import {InventoryStatsComponent} from './pages/inventory_stats/inventory_stats.component';
import {ListOfInfectedSurvivorsComponent} from './pages/reports/list-of-infected-survivors/list-of-infected-survivors.component';
import {ListOfNonInfectedSurvivorsComponent} from './pages/reports/list-of-non-infected-survivors/list-of-non-infected-survivors.component';
import {PercentageOfNonInfectedSurvivorsComponent} from './pages/reports/percentage-of-non-infected-survivors.component';
import { PercentageOfInfectedSurvivorsComponent } from './pages/reports/percentage-of-infected-survivors.component';

export const routes: Routes = [
  //Important:The sequence of path is important
  { path: '', redirectTo: '/home/dashboard/robot', pathMatch: 'full' },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard],
    children: [  // Children paths are appended to the parent path
        { path: '', redirectTo: '/home/dashboard/robot', pathMatch: 'full', data: [{selectedHeaderItemIndex: 1, selectedSubNavItemIndex: -1}] },  // Default path (if no deep path is specified for home component like webui/home then it will by default show ProductsComponent )
        {
            path     : 'dashboard',
            component: DashboardComponent,
            data     : [{selectedHeaderItemIndex: 0, selectedSubNavItemIndex: -1}],
            children : [
                { path: ''        , redirectTo: '/home/dashboard/robot', pathMatch: 'full'},
                { path: 'inventory'   , component: InventoryStatsComponent     , data: [{selectedHeaderItemIndex: 0, selectedSubNavItemIndex: 0}]  },
                { path: 'robot'   , component: RobotStatsComponent     , data: [{selectedHeaderItemIndex: 0, selectedSubNavItemIndex: 1}]  },
            ]
        },
        {
            path     : 'survivors',
            component: DashboardComponent,
            data     : [{selectedHeaderItemIndex: 1, selectedSubNavItemIndex: -1}],
            children : [
                { path: ''        , redirectTo: '/home/survivors/list', pathMatch: 'full'},
                { path: 'list'   , component: SurvivorsComponent     , data: [{selectedHeaderItemIndex: 1, selectedSubNavItemIndex: 0}]  },
                { path: 'register'   , component: SurvivorRegistrationComponent     , data: [{selectedHeaderItemIndex: 1, selectedSubNavItemIndex: 1}]  },
                { path: 'update/:id' , component: SurvivorDetailsComponent   , data: [{selectedHeaderItemIndex: 1, selectedSubNavItemIndex: 2}]  },
                { path: ':id/inventory' , component: SurvivorInventoryComponent   , data: [{selectedHeaderItemIndex: 1, selectedSubNavItemIndex: 2}]  },
                { path: 'location-update/:id' , component: SurvivorLocationComponent   , data: [{selectedHeaderItemIndex: 1, selectedSubNavItemIndex: 3}]  }
            ]
        },
        {
            path     : 'reports',
            component: DashboardComponent,
            data     : [{selectedHeaderItemIndex: 2, selectedSubNavItemIndex: -1}],
            children : [
                { path: ''        , redirectTo: '/home/reports/list-of-robots', pathMatch: 'full'},
                { path: 'percentage-of-infected-survivors'   , component: PercentageOfInfectedSurvivorsComponent     , data: [{selectedHeaderItemIndex: 2, selectedSubNavItemIndex: 0}]  },
                { path: 'percentage-of-non-infected-survivors'   , component: PercentageOfNonInfectedSurvivorsComponent     , data: [{selectedHeaderItemIndex: 2, selectedSubNavItemIndex: 1}]  },
                { path: 'list-of-infected-survivors'   , component: ListOfInfectedSurvivorsComponent     , data: [{selectedHeaderItemIndex: 2, selectedSubNavItemIndex: 2}]  },
                { path: 'list-of-non-infected-survivors'   , component: ListOfNonInfectedSurvivorsComponent     , data: [{selectedHeaderItemIndex: 2, selectedSubNavItemIndex: 3}]  },
                { path: 'list-of-robots'   , component: RobotsComponent     , data: [{selectedHeaderItemIndex: 2, selectedSubNavItemIndex: 4}]  },
            ]
        },
    ]
  },
  { path: 'login' , component: LoginComponent       , data: [{selectedHeaderItemIndex: -1, selectedSubNavItemIndex: -1}] },
  { path: 'logout', component: LogoutComponent      , data: [{selectedHeaderItemIndex: -1, selectedSubNavItemIndex: -1}] },
  { path: '**'    , component: PageNotFoundComponent, data: [{selectedHeaderItemIndex: -1, selectedSubNavItemIndex: -1}] }

];
@NgModule({
  imports: [ RouterModule.forRoot(routes, {useHash: true} )],
  exports: [ RouterModule ],
  declarations: [PageNotFoundComponent]
})
export class AppRoutingModule {}
