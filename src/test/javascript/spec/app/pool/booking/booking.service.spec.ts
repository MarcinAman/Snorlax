import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PoolBookingService } from 'app/pool/pool-booking/pool-booking.service';
import { SERVER_API_URL } from 'app/app.constants';
import { JhiAlertService } from 'ng-jhipster';
import { MockAlertService } from '../../../helpers/mock-alert.service';
import { JhiAlertErrorComponent } from 'app/shared';

describe('Service Tests', () => {
    describe('Booking Service', () => {
        let httpMock;
        let bookingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule],
                providers: [
                    {
                        provide: JhiAlertService,
                        useClass: MockAlertService
                    }
                ]
            })
                .overrideTemplate(JhiAlertErrorComponent, '')
                .compileComponents();

            bookingService = TestBed.get(PoolBookingService);
            httpMock = TestBed.get(HttpTestingController);
        });

        afterEach(() => {
            httpMock.verify();
        });

        describe('Service methods', () => {
            it('should call correct URL', () => {
                const poolBookingComponent = {
                    poolId: 'pool',
                    count: 1,
                    from: new Date('2011-10-05T14:48:00.000Z'),
                    to: new Date('2012-10-05T14:48:00.000Z')
                };

                bookingService.book(poolBookingComponent).subscribe(() => {});

                const req = httpMock.expectOne({ method: 'POST' });
                const resourceUrl = SERVER_API_URL + '/api/reserve';
                expect(req.request.url).toEqual(resourceUrl);
            });
            it('should propagate not found response', () => {
                const poolBookingComponent = {
                    poolId: 'samplepool',
                    count: 1,
                    from: new Date('2011-10-05T14:48:00.000Z'),
                    to: new Date('2012-10-05T14:48:00.000Z')
                };

                bookingService.book(poolBookingComponent).subscribe(null, (_error: any) => {
                    expect(_error.status).toEqual(404);
                });

                const req = httpMock.expectOne({ method: 'POST' });
                req.flush('Invalid request parameters', {
                    status: 404,
                    statusText: 'Bad Request'
                });
            });
        });
    });
});
