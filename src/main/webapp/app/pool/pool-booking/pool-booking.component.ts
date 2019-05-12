import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PoolBookingService } from 'app/pool/pool-booking/pool-booking.service';
import { Observable } from 'rxjs';
import { of } from 'rxjs/internal/observable/of';
import { Tool } from 'app/pool/tool';
import { AdditionalTools } from 'app/pool/additional-tools';

@Component({
    selector: 'jhi-pool-booking',
    templateUrl: './pool-booking.component.html',
    styles: []
})
export class PoolBookingComponent implements OnInit {
    public reservationPoolId: string;
    public count: number;
    public tools: Observable<any[]>;
    public selectedTools: Tool[];
    additionalTools: AdditionalTools;

    constructor(private route: ActivatedRoute, private poolBookingService: PoolBookingService, private elementRef: ElementRef) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe(e => (this.reservationPoolId = e.get('id')));
        this.count = 1;
        this.poolBookingService.getTools(this.reservationPoolId).subscribe(tools => {
            this.tools = of(tools.body);
        });
        this.selectedTools = [];
    }

    book(): void {
        this.poolBookingService.book(this.reservationPoolId, this.count).subscribe(this.previousState);
        if (this.selectedTools.length !== 0) {
            this.additionalTools = new AdditionalTools(this.reservationPoolId, this.selectedTools.map(tool => tool.name));
            this.poolBookingService.sendToolsRequest(this.additionalTools).subscribe(this.previousState);
        }
    }

    previousState() {
        window.history.back();
    }
}
