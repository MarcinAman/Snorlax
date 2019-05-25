import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { VmStatistics, VmUserStatistics } from 'app/admin/statistics/vm-statistics.model';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class VmStatisticsService {
    constructor(private http: HttpClient) {}

    getVmStatistics(from: Date, to: Date): Observable<VmStatistics> {
        return this.http.get<VmStatistics>(SERVER_API_URL + 'api/statistics/vm-usage/2010-01-01/2019-02-01');
    }

    getVmUserStatistics(from: Date, to: Date): Observable<VmUserStatistics> {
        return this.http.get<VmUserStatistics>(SERVER_API_URL + 'api/statistics/vm-user-usage/2010-01-01/2019-02-01');
    }
}
