import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { LoginService } from 'app/core/login/login.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { SnorlaxTestModule } from '../../../test.module';
import { MockLoginService } from '../../../helpers/mock-login.service';
import { MockStateStorageService } from '../../../helpers/mock-state-storage.service';
import { LoginComponent } from 'app/account';

describe('Component Tests', () => {
    describe('LoginComponent', () => {
        let comp: LoginComponent;
        let fixture: ComponentFixture<LoginComponent>;
        let mockLoginService: any;
        let mockStateStorageService: any;
        let mockRouter: any;
        let mockEventManager: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SnorlaxTestModule],
                declarations: [LoginComponent],
                providers: [
                    {
                        provide: LoginService,
                        useClass: MockLoginService
                    },
                    {
                        provide: StateStorageService,
                        useClass: MockStateStorageService
                    }
                ]
            })
                .overrideTemplate(LoginComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LoginComponent);
            comp = fixture.componentInstance;
            mockLoginService = fixture.debugElement.injector.get(LoginService);
            mockStateStorageService = fixture.debugElement.injector.get(StateStorageService);
            mockRouter = fixture.debugElement.injector.get(Router);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
        });

        it('should authenticate the user upon login when previous state was set', inject(
            [],
            fakeAsync(() => {
                // GIVEN
                const credentials = {
                    username: 'admin',
                    password: 'admin',
                    rememberMe: true
                };
                comp.username = 'admin';
                comp.password = 'admin';
                comp.rememberMe = true;
                comp.credentials = credentials;
                mockLoginService.setResponse({});
                mockStateStorageService.setResponse({ redirect: '' });

                // WHEN/
                comp.login();
                tick(); // simulate async

                // THEN
                expect(comp.authenticationError).toEqual(false);
                expect(mockEventManager.broadcastSpy).toHaveBeenCalledTimes(1);
                expect(mockLoginService.loginSpy).toHaveBeenCalledWith(credentials);
                expect(mockRouter.navigateSpy).toHaveBeenCalledTimes(1);
            })
        ));

        it('should authenticate the user upon login when previous state was not set', inject(
            [],
            fakeAsync(() => {
                // GIVEN
                const credentials = {
                    username: 'admin',
                    password: 'admin',
                    rememberMe: true
                };
                comp.username = 'admin';
                comp.password = 'admin';
                comp.rememberMe = true;
                comp.credentials = credentials;
                mockLoginService.setResponse({});
                mockStateStorageService.setResponse(null);

                // WHEN
                comp.login();
                tick(); // simulate async

                // THEN
                expect(comp.authenticationError).toEqual(false);
                expect(mockEventManager.broadcastSpy).toHaveBeenCalledTimes(1);
                expect(mockLoginService.loginSpy).toHaveBeenCalledWith(credentials);
                expect(mockRouter.navigateSpy).toHaveBeenCalledTimes(1);
            })
        ));

        it('should redirect user when register', () => {
            // WHEN
            comp.register();

            // THEN
            expect(mockRouter.navigateSpy).toHaveBeenCalledWith(['/register']);
        });

        it('should redirect user when request password', () => {
            // WHEN
            comp.requestResetPassword();

            // THEN
            expect(mockRouter.navigateSpy).toHaveBeenCalledWith(['/reset', 'request']);
        });
    });
});
