import { Injectable } from '@angular/core';
import { KeycloakEventType, KeycloakService } from 'keycloak-angular';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isLoggedIn$ = new BehaviorSubject<boolean>(false);
  username$ = new BehaviorSubject<string>('');
  constructor(private readonly keycloak: KeycloakService) {
    this.getUsername();
    this.isLoggedIn();
  }

  login(): void {
    this.keycloak.login();
  }

  logout(): void {
    this.keycloak.logout();
  }

  getUsername(): void {
    this.isLoggedIn$.subscribe({
      next: (val) => {
        if (val) {
          this.username$.next(this.keycloak.getUsername());
        }
      },
    });
  }

  isLoggedIn(): boolean {
    this.isLoggedIn$.next(this.keycloak.isLoggedIn());
    return this.keycloak.isLoggedIn();
  }
}
