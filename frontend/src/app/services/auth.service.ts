import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private readonly keycloak: KeycloakService) {}

  login(): void {
    this.keycloak.login();
  }

  logout(): void {
    this.keycloak.logout();
  }

  getUsername(): string {
    return this.keycloak.getUsername();
  }

  isLoggedIn(): boolean {
    return this.keycloak.isLoggedIn();
  }
}
