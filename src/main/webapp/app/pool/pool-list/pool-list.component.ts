import { Component, OnInit } from '@angular/core';
import { PoolListService } from 'app/pool/pool-list/pool-list.service';
import { Pool } from 'app/pool/pool';
import { Tool } from 'app/pool/tool';
import { Observable } from 'rxjs/index';
import { of } from 'rxjs/internal/observable/of';

@Component({
    selector: 'jhi-pool-list',
    templateUrl: './pool-list.component.html',
    styles: []
})
export class PoolListComponent implements OnInit {
    pools: Pool[];
    public displayName: string;
    public minimum: number;
    public maximum: number;
    public enabled: string;
    public selectedTools: Tool[] = [];
    public tools: Observable<any[]>;

    constructor(private poolService: PoolListService) {}

    ngOnInit() {
        this.poolService.getPools().subscribe(pools => {
            this.pools = pools.body;
        });
        this.poolService.getTools().subscribe(tools => {
            this.tools = of(tools.body);
        });
    }

    getFilteredPools() {
        this.poolService.getFilteredPools(this).subscribe(pools => {
            this.pools = pools.body;
        });
    }
}
