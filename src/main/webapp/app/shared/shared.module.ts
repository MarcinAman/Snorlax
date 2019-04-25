import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { SnorlaxSharedLibsModule, SnorlaxSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [SnorlaxSharedLibsModule, SnorlaxSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [],
    exports: [SnorlaxSharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnorlaxSharedModule {
    static forRoot() {
        return {
            ngModule: SnorlaxSharedModule
        };
    }
}
