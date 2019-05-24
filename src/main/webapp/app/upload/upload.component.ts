import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UploadEvent, UploadFile, FileSystemFileEntry } from 'ngx-file-drop';
import { UploadService } from './upload.service';

@Component({
    selector: 'jhi-home',
    templateUrl: './upload.component.html',
    styleUrls: ['upload.css']
})
export class UploadComponent {
    error: string;
    uploadResponse = { status: '', message: '' };

    public files: UploadFile[] = [];
    file: File;

    constructor(private uploadService: UploadService, private router: Router) {}

    onSubmit() {
        if (!this.file) {
            return (this.error = 'Please insert file');
        }
        this.clearField();
        const formData = new FormData();
        formData.append('file', this.file);
        this.uploadService.upload(formData).subscribe(
            async res => {
                if (res.status === 'success') {
                    try {
                        this.uploadService.addAlert('success', 'upload.upload-success');
                        await this.router.navigate(['/pool/list']);
                    } catch (e) {
                        this.error = e;
                    }
                }
                if (res.status === 'progress') {
                    return (this.uploadResponse = res);
                }
            },
            err => {
                this.clearField();
                if (err.status === 406) {
                    this.uploadService.addAlert('danger', 'upload.upload-err');
                } else {
                    this.error = err.message;
                }
            }
        );
    }

    public dropped(event: UploadEvent) {
        this.files = event.files;
        for (const droppedFile of event.files) {
            // Is it a file?
            if (droppedFile.fileEntry.isFile) {
                const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
                fileEntry.file((file: File) => {
                    this.clearField();
                    this.file = file;
                });
            }
        }
    }

    private clearField() {
        this.error = '';
        this.uploadResponse = { status: '', message: '' };
    }
}
