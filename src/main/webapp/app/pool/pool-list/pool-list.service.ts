import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pool } from 'app/pool/pool';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { PoolListComponent } from 'app/pool/pool-list/pool-list.component';
import { JhiAlertService } from 'ng-jhipster';
import { Tool } from 'app/pool/tool';

@Injectable({
    providedIn: 'root'
})
export class PoolListService {
    private link: string;
    alerts: any[];

    constructor(private http: HttpClient, private alertService: JhiAlertService) {
        this.alerts = this.alertService.get();
    }

    getPools(): Observable<HttpResponse<Pool[]>> {
        return this.http.get<Pool[]>(SERVER_API_URL + 'api/pool/list', { observe: 'response' });
    }

    getTools(): Observable<HttpResponse<Tool[]>> {
        return this.http.get<Tool[]>(SERVER_API_URL + 'api/tool/list', { observe: 'response' });
    }

    getFilteredPools(poolListComponent: PoolListComponent): Observable<HttpResponse<Pool[]>> {
        this.link = SERVER_API_URL + 'api/pool/list-filter?search=';

        if (poolListComponent.minimum > poolListComponent.maximum) {
            this.alertService.addAlert(
                {
                    type: 'danger',
                    msg: 'pools.list.minmax-err'
                },
                this.alerts
            );
        }

        if (poolListComponent.displayName !== undefined && poolListComponent.displayName.length > 0) {
            this.link = this.link + 'displayName:' + poolListComponent.displayName + ',';
        }
        if (poolListComponent.minimum > -1) {
            this.link = this.link + 'maximumCount>' + poolListComponent.minimum + ',';
        }
        if (poolListComponent.maximum > 0) {
            this.link = this.link + 'maximumCount<' + poolListComponent.maximum + ',';
        }
        if (poolListComponent.enabled !== undefined) {
            this.link = this.link + 'enabled:' + poolListComponent.enabled.valueOf() + ',';
        }
        poolListComponent.selectedTools.forEach(e => (this.link = this.link + 'tools:' + e.id + ','));

        return this.http.get<Pool[]>(this.link, { observe: 'response' });
    }
}
