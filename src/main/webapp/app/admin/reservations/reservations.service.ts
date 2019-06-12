import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { Reservation } from 'app/admin/reservations/reservation';

@Injectable({
    providedIn: 'root'
})
export class ReservationsService {
    constructor(private http: HttpClient) {}

    getAll(): Observable<Reservation[]> {
        return this.http.get<Reservation[]>(SERVER_API_URL + 'api/reservation-all/');
    }
}
