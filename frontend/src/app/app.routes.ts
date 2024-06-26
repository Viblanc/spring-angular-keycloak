import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { GameListComponent } from './components/game-list/game-list.component';
import { AuthGuard } from './guard/auth.guard';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'unauthorized', component: UnauthorizedComponent },
  {
    path: 'games/:username',
    component: GameListComponent,
    canActivate: [AuthGuard],
  },
  { path: '**', redirectTo: 'home' },
];
