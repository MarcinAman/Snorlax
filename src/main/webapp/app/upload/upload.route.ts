import { Route } from '@angular/router';

import { UploadComponent } from './';
import { UserRouteAccessService } from 'app/core';

export const UPLOAD_ROUTE: Route = {
    path: 'upload',
    component: UploadComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'global.menu.upload'
    },
    canActivate: [UserRouteAccessService]
};
