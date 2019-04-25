import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PoolBookingService } from 'app/pool/pool-booking/pool-booking.service';

@Component({
    selector: 'jhi-pool-booking',
    templateUrl: './pool-booking.component.html',
    styles: []
})
export class PoolBookingComponent implements OnInit {
    public reservationPoolId: string;
    public count: number;

    constructor(private route: ActivatedRoute, private poolBookingService: PoolBookingService) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe(e => (this.reservationPoolId = e.get('id')));
        this.count = 1;
    }

    book(): void {
        this.poolBookingService.book(this.reservationPoolId, this.count).subscribe(this.previousState);
    }

    previousState() {
        window.history.back();
    }
}
