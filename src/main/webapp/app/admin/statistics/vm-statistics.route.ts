import { Route } from '@angular/router';
import { VmStatisticsComponent } from 'app/admin/statistics/vm-statistics.component';

export const vmRoute: Route = {
    path: 'statistics',
    component: VmStatisticsComponent,
    data: {
        pageTitle: 'metrics.title' // TODO
    }
};
