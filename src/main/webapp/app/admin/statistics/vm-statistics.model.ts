import { ChartOptions, ChartType } from 'chart.js';

export interface VmStatistics {
    displayName: string;
    poolId: string;
    timesUsed: number;
}

export interface VmUserStatistics {
    username: string;
    timesUsed: number;
}

export interface DateRange {
    from: Date;
    to: Date;
}

export interface StatisticChart {
    dateFrom: Date;
    dateTo: Date;
    data: any;
    options: ChartOptions;
    type: ChartType;
    labels: string[];
}
