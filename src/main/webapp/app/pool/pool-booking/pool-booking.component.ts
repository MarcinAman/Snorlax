import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PoolBookingService } from 'app/pool/pool-booking/pool-booking.service';
import { Observable } from 'rxjs';
import { of } from 'rxjs/internal/observable/of';
import { Tool } from 'app/pool/tool';
import { AdditionalTools } from 'app/pool/additional-tools';
import { Moment } from 'moment';
import {
    NgbCalendar,
    NgbDateAdapter,
    NgbDateNativeAdapter,
    NgbDateStruct,
    NgbTabChangeEvent,
    NgbTimeStruct
} from '@ng-bootstrap/ng-bootstrap';
import moment = require('moment');

@Component({
    selector: 'jhi-pool-booking',
    templateUrl: './pool-booking.component.html',
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateNativeAdapter }],
    styleUrls: ['pool-booking.css']
})
export class PoolBookingComponent implements OnInit {
    public reservationPoolId: string;
    public count: number;
    public tools: Observable<any[]>;
    public selectedTools: Tool[];
    additionalTools: AdditionalTools;
    public from: Date;
    public to: Date;
    public fromPeriod: Date;
    public fromTime: NgbTimeStruct;
    public toPeriod: Date;
    public toTime: NgbTimeStruct;
    public excludedDates: any[];
    today: NgbDateStruct;
    previousWeekNumber = 0;
    weeks: number;
    possibleDates: Moment[];
    activeTab = 'once';

    constructor(
        private route: ActivatedRoute,
        private poolBookingService: PoolBookingService,
        private elementRef: ElementRef,
        private calendar: NgbCalendar
    ) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe(e => (this.reservationPoolId = e.get('id')));
        this.count = 1;
        this.poolBookingService.getTools(this.reservationPoolId).subscribe(tools => {
            this.tools = of(tools.body);
        });
        this.selectedTools = [];
        this.today = this.calendar.getToday();
        this.fromPeriod = new Date(moment().format('YYYY-MM-DD'));
        this.toPeriod = new Date(moment().format('YYYY-MM-DD'));
        this.fromTime = { hour: 12, minute: 0, second: 0 };
        this.toTime = { hour: 13, minute: 30, second: 0 };
        this.weeks = 0;
    }

    book(): void {
        this.from = this.convertToDate(this.fromPeriod, this.fromTime);
        this.to = this.convertToDate(this.toPeriod, this.toTime);
        if (this.activeTab === 'weekly') {
            this.poolBookingService.bookWeekly(this).subscribe(this.previousState);
        } else {
            this.poolBookingService.book(this).subscribe(this.previousState);
            if (this.selectedTools.length !== 0) {
                this.additionalTools = new AdditionalTools(this.reservationPoolId, this.selectedTools.map(tool => tool.name));
                this.poolBookingService.sendToolsRequest(this.additionalTools).subscribe(this.previousState);
            }
        }
    }

    handelWeekChange(weeks) {
        const difference = weeks - this.previousWeekNumber;
        this.toPeriod = new Date(
            moment(this.toPeriod)
                .add(difference * 7, 'd')
                .toISOString()
        );
        this.previousWeekNumber = weeks;
        this.calculatePossibleDates();
    }

    handelDateChange() {
        const diffDays = moment(this.toPeriod).diff(moment(this.fromPeriod), 'days');
        if (diffDays > 0) {
            this.weeks = Math.floor(diffDays / 7);
        } else {
            this.weeks = 0;
        }
        this.calculatePossibleDates();
    }

    convertToDate(date: Date, time: NgbTimeStruct) {
        return new Date(
            moment(date)
                .add(time)
                .toISOString()
        );
    }

    calculatePossibleDates() {
        let exludedDate = moment(this.fromPeriod).add(7, 'd');
        const newPossibleDates = [];
        this.excludedDates = [];
        while (exludedDate.isBefore(moment(this.toPeriod))) {
            newPossibleDates.push({ name: exludedDate.format('DD-MM-YYYY'), id: newPossibleDates.length });
            exludedDate = exludedDate.add(7, 'd');
        }
        this.possibleDates = newPossibleDates;
        console.log(this.possibleDates);
    }

    setActiveTab(tabChangeEvent: NgbTabChangeEvent) {
        console.log(tabChangeEvent);
        this.activeTab = tabChangeEvent.nextId;
    }

    previousState() {
        window.history.back();
    }
}
