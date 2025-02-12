import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { KeycloakAngularModule } from 'keycloak-angular';
import { NavbarComponent } from './components/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    KeycloakAngularModule,
    NavbarComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'game-backlog';
}
