import { Routes } from '@angular/router';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { HomeComponent } from './components/home/home.component';
import { GameListComponent } from './components/game-list/game-list.component';
import { AuthGuard } from './guard/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'mygames', component: GameListComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'unauthorized' },
  { path: 'unauthorized', component: UnauthorizedComponent },
];
