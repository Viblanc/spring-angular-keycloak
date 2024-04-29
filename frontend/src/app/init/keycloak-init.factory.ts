import { HttpClient } from '@angular/common/http';
import { KeycloakEventType, KeycloakService } from 'keycloak-angular';
import { environment } from '../../environments/environment';

export function initializeKeycloak(
  keycloak: KeycloakService,
  http: HttpClient
) {
  const { apiUrl, keycloakUrl } = environment;
  return () => {
    keycloak.keycloakEvents$.subscribe({
      next: (event) => {
        if (event.type === KeycloakEventType.OnAuthSuccess) {
          http.get(`${apiUrl}/users/me`).subscribe({
            error: (err) => {
              console.error('login request failed: ' + err);
            },
          });
        }
      },
    });
    return keycloak.init({
      config: {
        url: keycloakUrl,
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
