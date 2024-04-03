import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { AuthGuard } from './guard/auth.guard';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { GamelistComponent } from './components/gamelist/gamelist.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

export const routes: Routes = [
  { path: '', component: HomepageComponent, pathMatch: 'full' },
  { path: '404', component: NotFoundComponent },
  { path: 'user', component: GamelistComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'unauthorized' },
  { path: 'unauthorized', component: UnauthorizedComponent }
];