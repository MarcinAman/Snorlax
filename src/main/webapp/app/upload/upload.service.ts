import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable, of } from 'rxjs';
import { Pool } from 'app/pool/pool';
import { POOLS_MOCK_NEW } from 'app/pool/pools.mock_new';

@Injectable({
    providedIn: 'root'
})
export class UploadService {
    poolMocksNew: Pool[];

    constructor(private httpClient: HttpClient) {
        this.poolMocksNew = POOLS_MOCK_NEW;
    }

    public upload(data) {
        const uploadURL = `${SERVER_API_URL}/api/pool/upload`;
        return this.httpClient
            .post<any>(uploadURL, data, {
                reportProgress: true,
                observe: 'events'
            })
            .pipe(
                map(event => {
                    switch (event.type) {
                        case HttpEventType.UploadProgress:
                            const progress = Math.round((100 * event.loaded) / event.total);
                            return { status: 'progress', message: progress };
                        case HttpEventType.Response:
                            return { ...event.body, status: 'success' };
                        default:
                            return `Unhandled event: ${event.type}`;
                    }
                })
            );
    }

    // public parse(_data): Observable<Pool[]> {
    //     return of(this.poolMocksNew);
    // }

    public parse(data) {
        const uploadURL = `${SERVER_API_URL}/api/pool/parse`;
        return this.httpClient
            .post<any>(uploadURL, data, {
                reportProgress: true,
                observe: 'events'
            })
            .pipe(
                map(event => {
                    switch (event.type) {
                        case HttpEventType.UploadProgress:
                            const progress = Math.round((100 * event.loaded) / event.total);
                            return { status: 'progress', message: progress };
                        case HttpEventType.Response:
                            return { ...event.body, status: 'success' };
                        default:
                            return `Unhandled event: ${event.type}`;
                    }
                })
            );
    }

    public save(data) {
        const uploadURL = `${SERVER_API_URL}/api/pool/upload`;
        return this.httpClient
            .post<any>(uploadURL, data, {
                reportProgress: true,
                observe: 'events'
            })
            .pipe(
                map(event => {
                    switch (event.type) {
                        case HttpEventType.UploadProgress:
                            const progress = Math.round((100 * event.loaded) / event.total);
                            return { status: 'progress', message: progress };
                        case HttpEventType.Response:
                            return { ...event.body, status: 'success' };
                        default:
                            return `Unhandled event: ${event.type}`;
                    }
                })
            );
    }
}
