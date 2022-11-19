
import { BrowserModule }    from '@angular/platform-browser';
import { BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { NgModule }         from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';

import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgxChartsModule    } from '@swimlane/ngx-charts';
import {ClarityModule, ClrRadioModule} from '@clr/angular';

import { AppRoutingModule } from './app-routing.module';

import { LegendComponent } from './components/legend/legend.component';

import { AppComponent }       from './app.component';
import { HomeComponent         } from './home.component';
import { LoginComponent        } from './pages/login/login.component';
import { LogoutComponent       } from './pages/logout/logout.component';
import { DashboardComponent    } from './pages/dashboard/dashboard.component';

import { AppConfig        } from './app-config';
import { UserInfoService  } from './services/user-info.service';
import { AuthGuard        } from './services/auth_guard.service';
import { ApiRequestService} from './services/api/api-request.service';
import { TranslateService } from './services/api/translate.service';
import { LoginService     } from './services/api/login.service';
import {SurvivorsComponent} from './pages/survivors/survivors.component';
import {SurvivorDetailsComponent} from './pages/survivor_details/survivor_details.component';
import {SurvivorService} from './services/api/survivor.service';
import {SurvivorRegistrationComponent} from './pages/survivor_registration/survivor_registration.component';
import {MapComponent} from './pages/map/map.component';
import {RobotStatsComponent} from './pages/robot_stats/robot_stats.component';
import {RobotService} from './services/api/robot.service';
import {RobotsComponent} from './pages/robots/robots.component';
import {SurvivorLocationComponent} from './pages/survivor_location/survivor_location.component';
import {SurvivorInventoryComponent} from './pages/survivor_inventory/survivor_inventory.component';
import {InventoryStatsComponent} from './pages/inventory_stats/inventory_stats.component';
import {InventoryService} from './services/api/inventory.service';
import {ListOfInfectedSurvivorsComponent} from './pages/reports/list-of-infected-survivors/list-of-infected-survivors.component';
import { ReportsService } from './services/api/reports.service';
import {ListOfNonInfectedSurvivorsComponent} from './pages/reports/list-of-non-infected-survivors/list-of-non-infected-survivors.component';
import {PercentageOfInfectedSurvivorsComponent} from './pages/reports/percentage-of-infected-survivors.component';
import {PercentageOfNonInfectedSurvivorsComponent} from './pages/reports/percentage-of-non-infected-survivors.component';


@NgModule({

    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,

        NgxDatatableModule,
        NgxChartsModule,
        ClarityModule.forChild(),

        AppRoutingModule,
        ClrRadioModule,

    ],

  declarations: [
    LegendComponent,

    AppComponent,
    HomeComponent,
    LoginComponent,
    LogoutComponent,
    DashboardComponent,
    SurvivorsComponent,
    SurvivorDetailsComponent,
    SurvivorRegistrationComponent,
    MapComponent,
    RobotsComponent,
    RobotStatsComponent,
    SurvivorLocationComponent,
    SurvivorInventoryComponent,
    InventoryStatsComponent,
    ListOfInfectedSurvivorsComponent,
    ListOfNonInfectedSurvivorsComponent,
    PercentageOfInfectedSurvivorsComponent,
    PercentageOfNonInfectedSurvivorsComponent,

  ],

  providers: [
    AuthGuard,
    UserInfoService,
    TranslateService,
    ApiRequestService,
    LoginService,
    SurvivorService,
    RobotService,
    InventoryService,
    ReportsService,
    AppConfig,
  ],

  bootstrap: [AppComponent]
})

export class AppModule { }
