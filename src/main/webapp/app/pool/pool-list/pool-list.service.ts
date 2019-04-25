import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Pool } from 'app/pool/pool';
import { POOLS_MOCK } from 'app/pool/pools.mock';
@Injectable({
    providedIn: 'root'
})
export class PoolListService {
    poolsMock: Pool[];

    constructor() {
        this.poolsMock = POOLS_MOCK;
    }

    getPools(): Observable<Pool[]> {
        return of(this.poolsMock);
    }
}
