import { Injectable, inject } from '@angular/core';
import Keycloak from 'keycloak-js';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isLoggedIn$ = new BehaviorSubject<boolean>(false);
  username$ = new BehaviorSubject<string>('');
  private readonly keycloak = inject(Keycloak);
  constructor() {
    this.getUsername();
    this.isLoggedIn();
  }

  login(): Promise<void> {
    return this.keycloak.login();
  }

  logout(): Promise<void> {
    return this.keycloak.logout();
  }

  getUsername(): void {
    this.isLoggedIn$.subscribe({
      next: (loggedIn) => {
        if (loggedIn) {
          this.keycloak.loadUserProfile().then(
            (profile) => {
              this.username$.next(profile.username!);
            },
            (err) => {
              console.log(err);
            }
          );
          this.username$.next(this.keycloak.profile?.username!);
        }
      },
    });
  }

  isLoggedIn(): boolean {
    const res = this.keycloak.authenticated ?? false;
    this.isLoggedIn$.next(res);
    return res;
  }
}
