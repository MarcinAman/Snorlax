import { Route } from '@angular/router';
import { PoolListComponent } from './pool-list/pool-list.component';

export const POOL_ROUTE: Route[] = [
    {
        path: 'pool/list',
        component: PoolListComponent
    }
];
