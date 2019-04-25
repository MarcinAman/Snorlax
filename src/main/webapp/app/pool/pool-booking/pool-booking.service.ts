import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class PoolBookingService {
    private apiURL = SERVER_API_URL;

    constructor(private http: HttpClient) {}

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
}
