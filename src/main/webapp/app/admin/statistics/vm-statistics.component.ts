import { Component, OnInit } from '@angular/core';
import { VmStatisticsService } from 'app/admin/statistics/vm-statistics.service';

@Component({
    selector: 'jhi-statistics-vm',
    templateUrl: './vm-statistics.component.html'
})
export class VmStatisticsComponent implements OnInit {
    constructor(private service: VmStatisticsService) {}

    ngOnInit(): void {
        this.service.getVmStatistics(null, null).subscribe(s => console.log(s));
        this.service.getVmUserStatistics(null, null).subscribe(s => console.log(s));
    }
}
