import { Component, OnInit } from '@angular/core';
import { VmStatisticsService } from 'app/admin/statistics/vm-statistics.service';
import { StatisticChart, DateRange } from './vm-statistics.model';

@Component({
    selector: 'jhi-statistics-vm',
    templateUrl: './vm-statistics.component.html'
})
export class VmStatisticsComponent implements OnInit {
    public vmStatisticsDate: DateRange;
    public vmUserStatisticsDate: DateRange;

    public vmStatisticsCharts: StatisticChart[];
    public vmUserStatisticsCharts: StatisticChart[];

    constructor(private service: VmStatisticsService) {
        this.vmStatisticsDate = {
            from: new Date(),
            to: new Date()
        };
        this.vmUserStatisticsDate = {
            from: new Date(),
            to: new Date()
        };

        this.vmStatisticsCharts = [];
        this.vmUserStatisticsCharts = [];
    }
    ngOnInit(): void {}

    addVmStatisticsChart() {
        this.service.getVmStatistics(this.vmStatisticsDate.from, this.vmStatisticsDate.to).subscribe(v => {
            const data = v.map(x => x.timesUsed);
            const labels = v.map(x => x.displayName);
            this.vmStatisticsCharts.push({
                dateFrom: this.vmUserStatisticsDate.from,
                dateTo: this.vmUserStatisticsDate.to,
                data: [{ data: data }],
                options: { responsive: true },
                type: 'bar',
                labels: labels
            });
        });
    }

    addVmUserStatisticsChart() {
        this.service.getVmUserStatistics(this.vmStatisticsDate.from, this.vmStatisticsDate.to).subscribe(v => {
            const data = v.map(x => x.timesUsed);
            const labels = v.map(x => x.username);
            this.vmUserStatisticsCharts.push({
                dateFrom: this.vmUserStatisticsDate.from,
                dateTo: this.vmUserStatisticsDate.to,
                data: [{ data: data }],
                options: { responsive: true },
                type: 'bar',
                labels: labels
            });
        });
    }

    deleteVmChart(index: number) {
        this.vmStatisticsCharts.splice(index, 1);
    }

    deleteVmUserChart(index: number) {
        this.vmUserStatisticsCharts.splice(index, 1);
    }
}
