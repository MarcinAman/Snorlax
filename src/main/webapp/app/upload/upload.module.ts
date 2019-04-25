import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FileDropModule } from 'ngx-file-drop';

import { SnorlaxSharedModule } from 'app/shared';
import { UPLOAD_ROUTE, UploadComponent } from './';

@NgModule({
    imports: [HttpClientModule, FileDropModule, SnorlaxSharedModule, RouterModule.forChild([UPLOAD_ROUTE])],
    declarations: [UploadComponent]
})
export class SnorlaxUploadModule {}
