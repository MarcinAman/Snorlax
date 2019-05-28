import { Route } from '@angular/router';
import { PoolListComponent } from './pool-list/pool-list.component';
import { PoolBookingComponent } from 'app/pool/pool-booking/pool-booking.component';
import { UserRouteAccessService } from 'app/core';

export const POOL_ROUTE: Route[] = [
    {
        path: 'pool/list',
        component: PoolListComponent,
        data: {
            authorities: ['ROLE_USER']
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pool/book/:id',
        component: PoolBookingComponent,
        data: {
            authorities: ['ROLE_USER']
        },
        canActivate: [UserRouteAccessService]
    }
];
