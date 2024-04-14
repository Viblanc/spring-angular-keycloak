import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Game } from '../models/game.model';
import { Observable } from 'rxjs';
import { UserGame } from '../models/user-game.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private apiUrl: String;
  constructor(private http: HttpClient) {
    this.apiUrl = environment.apiUrl;
  }

  getGames(): Observable<UserGame[]> {
    return this.http.get<UserGame[]>(`${this.apiUrl}/games`);
  }

  search(query: string): Observable<Game[]> {
    return this.http.get<Game[]>(`${this.apiUrl}/games/search?query=${query}`);
  }

  addGame(game: Game): Observable<Game[]> {
    return this.http.post<Game[]>(`${this.apiUrl}/games/add`, game);
  }

  updateGame(game: UserGame): Observable<UserGame> {
    return this.http.put<UserGame>(
      `${this.apiUrl}/games/${game.game.id}`,
      game
    );
  }

  removeGame(gameId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/games/${gameId}`);
  }
}
