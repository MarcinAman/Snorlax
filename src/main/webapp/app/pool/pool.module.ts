import { NgModule } from '@angular/core';
import { PoolListComponent } from 'app/pool/pool-list/pool-list.component';
import { SnorlaxSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { POOL_ROUTE } from 'app/pool/pool.route';

@NgModule({
    imports: [SnorlaxSharedModule, RouterModule.forChild(POOL_ROUTE)],
    declarations: [PoolListComponent]
})
export class PoolModule {}
