import { Injectable } from '@angular/core';

@Injectable()
export class AppConfig {
    public version = '1.0.0';
    public locale  = 'en-US';
    public currencyFormat = { style: 'currency', currency: 'USD' };
    public dateFormat     = { year: 'numeric', month: 'short', day: 'numeric'};

    // API Related configs
    public apiPort = '8181';
    public apiProtocol: string;
    public apiHostName: string;
    public baseApiPath: string;

    constructor() {
        if (this.apiProtocol === undefined) {
            this.apiProtocol = window.location.protocol;
        }
        if (this.apiHostName === undefined) {
            this.apiHostName = window.location.hostname;
        }
        if (this.apiPort === undefined) {
            this.apiPort = window.location.port;
        }
        if (this.apiHostName.includes('infomud') || this.apiHostName.includes('heroku')) {
            this.baseApiPath = this.apiProtocol + '//' + this.apiHostName + '/';
        } else {
            this.baseApiPath = this.apiProtocol + '//' + this.apiHostName + ':' + this.apiPort + '/';
        }
        if (this.locale === undefined) {
            this.locale = navigator.language;
        }
    }
}
