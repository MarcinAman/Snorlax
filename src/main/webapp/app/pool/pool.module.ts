import { NgModule } from '@angular/core';
import { PoolListComponent } from 'app/pool/pool-list/pool-list.component';
import { SnorlaxSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { POOL_ROUTE } from 'app/pool/pool.route';
import { PoolBookingComponent } from 'app/pool/pool-booking/pool-booking.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';

@NgModule({
    imports: [SnorlaxSharedModule, RouterModule.forChild(POOL_ROUTE), NgSelectModule, FormsModule],
    declarations: [PoolListComponent, PoolBookingComponent]
})
export class PoolModule {}
