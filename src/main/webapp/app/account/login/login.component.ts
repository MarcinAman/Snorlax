import { Component, AfterViewInit, Renderer, ElementRef, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { LoginService } from 'app/core/login/login.service';
import { LocalStateStorageService } from 'app/core/auth/local-state-storage.service';

@Component({
    selector: 'jhi-login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements AfterViewInit, OnInit {
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;

    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private localStateStorageService: LocalStateStorageService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private router: Router
    ) {
        this.credentials = {};
    }

    ngOnInit() {
        this.username = this.localStateStorageService.getUsername();
        this.rememberMe = this.localStateStorageService.getRememberMe();
    }

    ngAfterViewInit(): void {
        setTimeout(() => this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []), 0);
    }

    login() {
        this.loginService
            .login({
                username: this.username,
                password: this.password,
                rememberMe: this.rememberMe
            })
            .then(() => {
                this.authenticationError = false;

                if (this.rememberMe) {
                    this.localStateStorageService.storeUsername(this.username);
                    this.localStateStorageService.storeRememberMe(String(this.rememberMe));
                } else {
                    this.localStateStorageService.storeUsername(null);
                    this.localStateStorageService.storeRememberMe(String(this.rememberMe));
                }

                if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                    this.router.navigate(['']);
                }

                this.eventManager.broadcast({
                    name: 'authenticationSuccess',
                    content: 'Sending Authentication Success'
                });

                this.router.navigate(['']);
            })
            .catch(() => {
                this.authenticationError = true;
            });
    }

    register() {
        this.router.navigate(['/register']);
    }

    requestResetPassword() {
        this.router.navigate(['/reset', 'request']);
    }
}
