import { Component, OnInit } from '@angular/core';
import { PoolListService } from 'app/pool/pool-list/pool-list.service';
import { Pool } from 'app/pool/pool';

@Component({
    selector: 'jhi-pool-list',
    templateUrl: './pool-list.component.html',
    styles: []
})
export class PoolListComponent implements OnInit {
    pools: Pool[];

    constructor(private poolService: PoolListService) {}

    ngOnInit() {
        this.poolService.getPools().subscribe(pools => (this.pools = pools));
    }
}
