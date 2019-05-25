import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pool } from 'app/pool/pool';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
@Injectable({
    providedIn: 'root'
})
export class PoolListService {
    constructor(private http: HttpClient) {}

    getPools(): Observable<HttpResponse<Pool[]>> {
        return this.http.get<Pool[]>(SERVER_API_URL + 'api/pool/list', { observe: 'response' });
    }
}
