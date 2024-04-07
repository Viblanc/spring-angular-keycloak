import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient } from '@angular/common/http';
import { Game } from '../models/game.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  constructor(private authService: AuthService, private http: HttpClient) {}

  search(query: string): Observable<Game[]> {
    return this.http.get<Game[]>(
      `http://localhost:9000/api/games/search?query=${query}`
    );
  }
}
