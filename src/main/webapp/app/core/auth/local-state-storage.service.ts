import { Injectable } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';

@Injectable({ providedIn: 'root' })
export class LocalStateStorageService {
    constructor(private $localStorage: LocalStorageService) {}
    storeRememberMe(rememberMe: string) {
        this.$localStorage.store('RememberMe', rememberMe);
    }

    getRememberMe() {
        return this.$localStorage.retrieve('RememberMe');
    }

    storeUsername(username: string) {
        this.$localStorage.store('username', username);
    }

    getUsername() {
        return this.$localStorage.retrieve('username');
    }
}
