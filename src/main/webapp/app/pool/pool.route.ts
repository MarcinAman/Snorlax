import { Route } from '@angular/router';
import { PoolListComponent } from './pool-list/pool-list.component';
import { PoolBookingComponent } from 'app/pool/pool-booking/pool-booking.component';

export const POOL_ROUTE: Route[] = [
    {
        path: 'pool/list',
        component: PoolListComponent
    },
    {
        path: 'pool/book/:id',
        component: PoolBookingComponent
    }
];
