import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { VmStatistics, VmUserStatistics } from 'app/admin/statistics/vm-statistics.model';
import { Injectable } from '@angular/core';
import * as moment from 'moment';

@Injectable({ providedIn: 'root' })
export class VmStatisticsService {
    constructor(private http: HttpClient) {}

    getVmStatistics(from: Date, to: Date): Observable<VmStatistics[]> {
        const fromStr = moment(from).format('YYYY-MM-DD');
        const toStr = moment(to).format('YYYY-MM-DD');
        return this.http.get<VmStatistics[]>(SERVER_API_URL + 'api/statistics/vm-usage/' + fromStr + '/' + toStr);
    }

    getVmUserStatistics(from: Date, to: Date): Observable<VmUserStatistics[]> {
        const fromStr = moment(from).format('YYYY-MM-DD');
        const toStr = moment(to).format('YYYY-MM-DD');
        return this.http.get<VmUserStatistics[]>(SERVER_API_URL + 'api/statistics/vm-user-usage/' + fromStr + '/' + toStr);
    }
}
