import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Subject, debounceTime, distinctUntilChanged, switchMap } from 'rxjs';
import { GameService } from '../../services/game.service';
import { Game } from '../../models/game.model';
import { FormsModule } from '@angular/forms';
import { GameComponent } from '../game/game.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, GameComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  games: Game[] = [];
  modalGame?: Game;
  gameName: string = '';
  private query$ = new Subject<string>();
  showModal = false;

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

  toggleModal(game: Game) {
    this.showModal = !this.showModal;
    this.modalGame = this.modalGame ? undefined : game;
  }

  search(): void {
    this.query$.next(this.gameName);
  }

  addToList(): void {
    console.log(this.modalGame);
    this.gameService.addGame(this.modalGame!).subscribe({
      next: () => {
        this.toggleModal({});
      },
      error: (err) => {
        this.toggleModal({});
        console.log(err);
      },
    });
  }
}
