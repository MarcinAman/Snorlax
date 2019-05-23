import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import { Pool } from 'app/pool/pool';

@Injectable({
    providedIn: 'root'
})
export class UploadService {
    constructor(private httpClient: HttpClient) {}

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

    public parse(data) {
        const uploadURL = `${SERVER_API_URL}/api/pool/parse`;
        return this.httpClient.post<Pool[]>(uploadURL, data, {
            reportProgress: true,
            observe: 'response'
        });
    }

    public save(data) {
        const uploadURL = `${SERVER_API_URL}/api/pool/save`;
        return this.httpClient
            .post(uploadURL, data, {
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
