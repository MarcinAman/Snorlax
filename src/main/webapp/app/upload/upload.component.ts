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
                console.log(res);
                if (res.status === 'success') {
                    try {
                        await this.router.navigate(['/pool/list']);
                    } catch (e) {
                        this.error = e;
                    }
                }
                if (typeof res !== 'string') {
                    return (this.uploadResponse = res);
                }
            },
            err => {
                return (this.error = err.message);
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
