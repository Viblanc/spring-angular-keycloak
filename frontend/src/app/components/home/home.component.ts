import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Subject, debounceTime, distinctUntilChanged, switchMap } from 'rxjs';
import { GameService } from '../../services/game.service';
import { Game } from '../../models/game.model';
import { FormsModule } from '@angular/forms';
import { GameComponent } from '../game/game.component';
import { ModalComponent } from '../modal/modal.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, GameComponent, ModalComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  games: Game[] = [];
  gameName: string = '';
  private query$ = new Subject<string>();
  modalGame$ = new Subject<Game>();
  isLoading = false;

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
          this.games = games;
          this.isLoading = false;
        },
        error: (err) => {
          console.log(err);
          this.isLoading = false;
        },
      });
  }

  search(): void {
    this.query$.next(this.gameName);
    this.isLoading = true;
  }

  addToList(game: Game): void {
    this.gameService.addGame(game).subscribe({
      error: (err) => {
        console.log(err);
      },
    });
  }
}
