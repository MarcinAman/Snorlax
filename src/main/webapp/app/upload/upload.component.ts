import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FileSystemFileEntry, UploadEvent, UploadFile } from 'ngx-file-drop';
import { UploadService } from './upload.service';
import { Pool } from 'app/pool/pool';
import { PoolListService } from 'app/pool/pool-list/pool-list.service';

@Component({
    selector: 'jhi-home',
    templateUrl: './upload.component.html',
    styleUrls: ['upload.css']
})
export class UploadComponent {
    error: string;
    uploadResponse = { status: '', message: '' };

    currentPools: Map<String, Pool> = new Map<String, Pool>();
    newPools: Map<String, Pool> = new Map<String, Pool>();

    uploaded = false;

    public files: UploadFile[] = [];

    public merged: Pool[] = [];
    public file: File;
    public conflicts = [];

    constructor(private uploadService: UploadService, private router: Router, private poolService: PoolListService) {}

    async onSubmit() {
        if (!this.file) {
            return (this.error = 'Please insert file');
        }
        this.clearField();
        const formData = new FormData();
        formData.append('file', this.file);

        const oldOnesPromise = this.poolService.getPools().toPromise();
        const newOnesPromise = this.uploadService.parse(formData).toPromise();

        try {
            (await oldOnesPromise).body.forEach(pool => this.currentPools.set(pool.poolId, pool));
            (await newOnesPromise).body.forEach(pool => this.newPools.set(pool.poolId, pool));
        } catch (err) {
            this.clearField();
            if (err.status === 406) {
                this.uploadService.addAlert('danger', 'upload.upload-json-err');
            }
            this.uploaded = false;
            return (this.error = err.message);
        }
        this.getConflicts();
        this.uploaded = true;
    }

    onSave() {
        this.conflicts.forEach(conflict => {
            const chosen = conflict.chosen === 'new' ? conflict.newPool : conflict.currentPool;
            this.merged.push(chosen);
        });
        this.uploadService.save(JSON.stringify(this.merged)).subscribe(
            async res => {
                if (res.status === 'success') {
                    try {
                        this.uploadService.addAlert('success', 'upload.upload-json-success');
                        await this.router.navigate(['/pool/list']);
                    } catch (e) {
                        this.error = e;
                    }
                }
            },
            err => {
                this.clearField();
                if (err.status === 406) {
                    this.uploadService.addAlert('danger', 'upload.upload-json-err');
                } else {
                    this.error = err.message;
                }
            }
        );
    }

    onForceSave() {
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

    private getConflicts() {
        this.conflicts = [];
        this.newPools.forEach((newPool, key) => {
            if (this.currentPools.has(key)) {
                const currentPool = this.currentPools.get(key);
                if (newPool.maximumCount !== currentPool.maximumCount) {
                    this.conflicts.push({ newPool, currentPool, chosen: 'new' });
                }
            } else {
                this.merged.push(newPool);
            }
        });
    }
}
