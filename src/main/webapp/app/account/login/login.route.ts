import { Route } from '@angular/router';

import { LoginComponent } from './login.component';

export const loginRoute: Route = {
    path: 'login',
    component: LoginComponent,
    data: {
        authorities: [],
        pageTitle: 'login.title'
    }
};
