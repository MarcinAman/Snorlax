import { Routes } from '@angular/router';

import { auditsRoute, configurationRoute, docsRoute, healthRoute, logsRoute, metricsRoute, userMgmtRoute } from './';

import { UserRouteAccessService } from 'app/core';
import { vmRoute } from 'app/admin/statistics/vm-statistics.route';
import { reservationRoute } from 'app/admin/reservations/reservation.route';

const ADMIN_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    ...userMgmtRoute,
    metricsRoute,
    vmRoute,
    reservationRoute
];

export const adminState: Routes = [
    {
        path: '',
        data: {
            authorities: ['ROLE_ADMIN']
        },
        canActivate: [UserRouteAccessService],
        children: ADMIN_ROUTES
    }
];
