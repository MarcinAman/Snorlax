import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PoolBookingService } from 'app/pool/pool-booking/pool-booking.service';
import { SERVER_API_URL } from 'app/app.constants';

describe('Service Tests', () => {
    describe('Booking Service', () => {
        let httpMock;
        let bookingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });

            bookingService = TestBed.get(PoolBookingService);
            httpMock = TestBed.get(HttpTestingController);
        });

        afterEach(() => {
            httpMock.verify();
        });

        describe('Service methods', () => {
            it('should call correct URL', () => {
                bookingService.book('pool', 1).subscribe(() => {});

                const req = httpMock.expectOne({ method: 'GET' });
                const resourceUrl = SERVER_API_URL + '/api/reserve';
                expect(req.request.url).toEqual(resourceUrl);
            });

            it('should call URL with correct params', () => {
                const poolId = 'samplepool';
                const count = 1;

                bookingService.book(poolId, count).subscribe(() => {});

                const req = httpMock.expectOne({ method: 'GET' });

                const params = req.request.params;
                expect(params.has('poolId')).toBeTruthy();
                expect(params.has('count')).toBeTruthy();
                expect(params.keys()).toEqual(['poolId', 'count']);
            });

            it('should call URL with correct values in params', () => {
                const poolId = 'samplepool';
                const count = 1;

                bookingService.book(poolId, count).subscribe(() => {});

                const req = httpMock.expectOne({ method: 'GET' });

                const params = req.request.params;

                expect(params.get('poolId')).toEqual(poolId);
                expect(params.get('count')).toEqual(count + '');
            });

            it('should propagate not found response', () => {
                bookingService.book('samplepool', 1).subscribe(null, (_error: any) => {
                    expect(_error.status).toEqual(404);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush('Invalid request parameters', {
                    status: 404,
                    statusText: 'Bad Request'
                });
            });
        });
    });
});
