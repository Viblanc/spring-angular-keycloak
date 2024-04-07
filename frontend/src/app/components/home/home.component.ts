import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Subject, debounceTime, distinctUntilChanged, switchMap } from 'rxjs';
import { GameService } from '../../services/game.service';
import { Game } from '../../models/game.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  games: Game[] = [];
  gameName: string = '';
  private query$ = new Subject<string>();

  constructor(
    protected readonly authService: AuthService,
    private gameService: GameService
  ) {}

  ngOnInit(): void {
    this.query$
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        switchMap((query) => this.gameService.search(query))
      )
      .subscribe({
        next: (games) => {
          console.log(games);
          this.games = games;
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  search(): void {
    this.query$.next(this.gameName);
  }
}
