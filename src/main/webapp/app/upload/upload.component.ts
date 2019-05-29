import { ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import { FileSystemFileEntry, UploadEvent, UploadFile } from 'ngx-file-drop';
import { UploadService } from './upload.service';
import { Pool } from 'app/pool/pool';
import { PoolListService } from 'app/pool/pool-list/pool-list.service';

enum PoolType {
    New,
    Old
}

@Component({
    selector: 'jhi-home',
    templateUrl: './upload.component.html',
    styleUrls: ['upload.css']
})
export class UploadComponent {
    error: string;
    uploadResponse = { status: '', message: '' };

    currentPool: Map<String, Pool> = new Map<String, Pool>();
    newPool: Map<String, Pool> = new Map<String, Pool>();

    public poolType = PoolType;
    public isCollapsed = true;
    public files: UploadFile[] = [];

    public currentConflictPool: Pool[] = [];
    public newConflictPool: Pool[] = [];
    public merged: Pool[] = [];
    public choosePattern = new Map();
    public file: File;

    constructor(
        private uploadService: UploadService,
        private router: Router,
        private poolService: PoolListService,
        private changeDetectorRef: ChangeDetectorRef
    ) {}

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
            (await oldOnesPromise).body.forEach(pool => this.currentPool.set(pool.poolId, pool));
            (await newOnesPromise).body.forEach(pool => this.newPool.set(pool.poolId, pool));
        } catch (err) {
            this.clearField();
            if (err.status === 406) {
                this.uploadService.addAlert('danger', 'upload.upload-json-err');
            } else {
                return (this.error = err.message);
            }
            this.isCollapsed = true;
        }
        this.getConflicts();
        this.isCollapsed = false;
        this.newConflictPool.forEach((pool, _) => this.choosePattern.set(pool.poolId, this.poolType.New));
        this.changeDetectorRef.detectChanges();
    }

    onSave() {
        this.choosePattern.forEach((value, key) => {
            let item;
            if (value === this.poolType.New) {
                item = this.newPool.get(key);
            } else {
                item = this.currentPool.get(key);
            }
            this.merged.push(item);
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
        this.newConflictPool = [];
        this.currentConflictPool = [];
        this.newPool.forEach((value, key) => {
            if (this.currentPool.has(key)) {
                const current = this.currentPool.get(key);
                if (value.maximumCount !== current.maximumCount) {
                    this.newConflictPool.push(value);
                    this.currentConflictPool.push(current);
                }
            } else {
                this.merged.push(value);
            }
        });
    }
}
