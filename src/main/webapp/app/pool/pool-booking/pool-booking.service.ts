import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { Tool } from 'app/pool/tool';
import { AdditionalTools } from 'app/pool/additional-tools';

@Injectable({
    providedIn: 'root'
})
export class PoolBookingService {
    private apiURL = SERVER_API_URL;

    constructor(private http: HttpClient) {}

    getTools(poolId: string): Observable<HttpResponse<Tool[]>> {
        return this.http.get<Tool[]>(SERVER_API_URL + 'api/tool/list/' + poolId, { observe: 'response' });
    }

    book(poolId: string, count: number): Observable<HttpResponse<void>> {
        const params = new HttpParams({
            fromObject: {
                poolId: poolId,
                count: count + ''
            }
        });

        return this.http.get<void>(this.apiURL + '/api/reserve', {
            observe: 'response',
            params: params
        });
    }

    sendToolsRequest(additionalTools: AdditionalTools): Observable<HttpResponse<void>> {
        return this.http.post<void>(this.apiURL + '/api/request-tools', additionalTools, { observe: 'response' });
    }
}
