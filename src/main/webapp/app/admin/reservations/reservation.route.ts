import { Route } from '@angular/router';
import { ReservationsComponent } from 'app/admin/reservations/reservations.component';

export const reservationRoute: Route = {
    path: 'reservations',
    component: ReservationsComponent,
    data: {
        pageTitle: 'reservations.title'
    }
};
