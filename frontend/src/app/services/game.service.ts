import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Game } from '../models/game.model';
import { Observable } from 'rxjs';
import { UserGame } from '../models/user-game.model';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  constructor(private http: HttpClient) {}

  getGames(): Observable<UserGame[]> {
    return this.http.get<UserGame[]>('http://localhost:9000/api/games');
  }

  search(query: string): Observable<Game[]> {
    return this.http.get<Game[]>(
      `http://localhost:9000/api/games/search?query=${query}`
    );
  }
}
