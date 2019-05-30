import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { Tool } from 'app/pool/tool';
import { AdditionalTools } from 'app/pool/additional-tools';
import { PoolBookingComponent } from 'app/pool/pool-booking/pool-booking.component';
import { JhiAlertService } from 'ng-jhipster';
import moment = require('moment');

@Injectable({
    providedIn: 'root'
})
export class PoolBookingService {
    alerts: any[];
    private apiURL = SERVER_API_URL;

    constructor(private http: HttpClient, private alertService: JhiAlertService) {
        this.alerts = this.alertService.get();
    }

    getTools(poolId: string): Observable<HttpResponse<Tool[]>> {
        return this.http.get<Tool[]>(SERVER_API_URL + 'api/tool/list/' + poolId, { observe: 'response' });
    }

    book(poolBookingComponent: PoolBookingComponent): Observable<HttpResponse<void>> {
        if (isNaN(poolBookingComponent.to.getTime())) {
            this.alertService.addAlert(
                {
                    type: 'danger',
                    msg: 'pools.booking.to-err'
                },
                this.alerts
            );
        }
        if (isNaN(poolBookingComponent.from.getTime())) {
            this.alertService.addAlert(
                {
                    type: 'danger',
                    msg: 'pools.booking.from-err'
                },
                this.alerts
            );
        }

        return this.http.post<HttpResponse<void>>(this.apiURL + '/api/reserve', {
            poolId: poolBookingComponent.reservationPoolId,
            count: poolBookingComponent.count + '',
            from: poolBookingComponent.from.toISOString(),
            to: poolBookingComponent.to.toISOString()
        });
    }

    bookWeekly(poolBookingComponent: PoolBookingComponent): Observable<HttpResponse<void>> {
        const { reservationPoolId, count, fromPeriod, fromTime, toPeriod, toTime, excludedDates } = poolBookingComponent;

        if (isNaN(fromPeriod.getTime())) {
            this.alertService.addAlert(
                {
                    type: 'danger',
                    msg: 'pools.booking.to-err'
                },
                this.alerts
            );
        }
        if (isNaN(toPeriod.getTime())) {
            this.alertService.addAlert(
                {
                    type: 'danger',
                    msg: 'pools.booking.from-err'
                },
                this.alerts
            );
        }
        return this.http.post<HttpResponse<void>>(this.apiURL + '/api/reserve-period', {
            poolId: reservationPoolId,
            count: count + '',
            fromTime: moment(fromPeriod)
                .add(fromTime)
                .toISOString(),
            toTime: moment(fromPeriod)
                .add(toTime)
                .toISOString(),
            toPeriod: toPeriod.toISOString(),
            fromPeriod: fromPeriod.toISOString(),
            leftOuts: excludedDates.map(date => moment(date).toISOString())
        });
    }

    sendToolsRequest(additionalTools: AdditionalTools): Observable<HttpResponse<void>> {
        return this.http.post<void>(this.apiURL + '/api/request-tools', additionalTools, { observe: 'response' });
    }
}
