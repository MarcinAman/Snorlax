import { Component } from '@angular/core';
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

    currentConflictPool: Pool[] = [];
    newConflictPool: Pool[] = [];

    merged: Pool[] = [];

    choosePattern = new Map();

    public files: UploadFile[] = [];
    file: File;

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

        const oldOnes = await oldOnesPromise;
        const newOnes = await newOnesPromise;

        oldOnes.body.forEach(pool => this.currentPool.set(pool.poolId, pool));
        newOnes.body.forEach(pool => this.newPool.set(pool.poolId, pool));

        if (this.currentConflictPool.length === 0 && this.newConflictPool.length === 0) {
            this.getConflicts();
        }
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
        const formData = new FormData();
        formData.append('pools', JSON.stringify(this.merged));
        this.uploadService.save(formData).subscribe(
            async res => {
                if (res === 'success') {
                    try {
                        await this.router.navigate(['/pool/list']);
                    } catch (e) {
                        this.error = e;
                    }
                }
            },
            err => {
                return (this.error = err.message);
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
        this.newPool.forEach((value, key) => {
            if (this.currentPool.has(key) && JSON.stringify(this.currentPool.get(key)) !== JSON.stringify(this.newPool.get(key))) {
                this.newConflictPool.push(value);
                this.currentConflictPool.push(value);
            } else {
                this.merged.push(value);
            }
        });
    }
}
