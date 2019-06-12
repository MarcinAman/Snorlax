import { Component, OnInit } from '@angular/core';
import { ReservationsService } from 'app/admin/reservations/reservations.service';
import { Reservation } from 'app/admin/reservations/reservation';
import * as moment from 'moment';

@Component({
    selector: 'jhi-reservations',
    templateUrl: './reservations.component.html',
    styles: []
})
export class ReservationsComponent implements OnInit {
    reservations: Reservation[];

    constructor(private reservationsService: ReservationsService) {}

    ngOnInit() {
        this.reservationsService.getAll().subscribe(value => {
            console.log(value);
            this.reservations = value.map(x => this.parseDates(x));
        });
    }

    private parseDates(reservation: Reservation): Reservation {
        return {
            ...reservation,
            from: moment(reservation.from).format('MM DD YYYY, h:mm:ss a'),
            to: moment(reservation.to).format('MM DD YYYY, h:mm:ss a')
        };
    }
}
