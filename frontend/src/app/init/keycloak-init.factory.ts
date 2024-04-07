import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { KeycloakEventType, KeycloakService } from 'keycloak-angular';

export function initializeKeycloak(
  keycloak: KeycloakService,
  http: HttpClient
) {
  return () => {
    keycloak.keycloakEvents$.subscribe({
      next: (event) => {
        if (event.type === KeycloakEventType.OnAuthSuccess) {
          http.get('http://localhost:9000/api/users/me').subscribe({
            error: (err) => {
              console.error('login request failed: ' + err);
            },
          });
        }
      },
      error: (err) => {
        console.error('erreur');
      },
    });
    return keycloak.init({
      config: {
        url: 'http://localhost:8080',
        realm: 'game-backlog',
        clientId: 'gamebacklog-app',
      },
      enableBearerInterceptor: true,
      bearerPrefix: 'Bearer',
      bearerExcludedUrls: ['/assets'],
      loadUserProfileAtStartUp: true,
      initOptions: {
        pkceMethod: 'S256',
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      },
    });
  };
}
