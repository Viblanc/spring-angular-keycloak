import { Component, Input, OnInit, Pipe, PipeTransform } from '@angular/core';
import { GameService } from '../../services/game.service';
import { CommonModule, NgFor } from '@angular/common';
import { UserGame } from '../../models/user-game.model';
import { FormsModule } from '@angular/forms';
import { GameComponent } from '../game/game.component';
import { AuthService } from '../../services/auth.service';

@Pipe({
  name: 'rangeNumber',
  standalone: true,
})
export class RangeNumber implements PipeTransform {
  transform(length: number) {
    return Array.from({ length: length + 1 }, (_, k) => k);
  }
}

enum Selection {
  All,
  Not_Completed,
  Favourite,
}

@Component({
  selector: 'app-game-list',
  standalone: true,
  imports: [NgFor, FormsModule, CommonModule, GameComponent, RangeNumber],
  templateUrl: './game-list.component.html',
  styleUrl: './game-list.component.css',
})
export class GameListComponent implements OnInit {
  games: UserGame[] = [];
  selectedGames: UserGame[] = [];
  selection: typeof Selection = Selection;
  @Input() username = '';

  constructor(
    private gameService: GameService,
    protected readonly authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe({
      next: (loggedIn) => {
        if (loggedIn) {
          this.gameService.getGames(this.username).subscribe({
            next: (games) => {
              this.games = games.sort((a, b) => {
                if (a.game.name! > b.game.name!) {
                  return 1;
                } else if (b.game.name! > a.game.name!) {
                  return -1;
                } else {
                  return 0;
                }
              });
              this.selectedGames = this.games;
            },
            error: (err) => {
              console.error(err);
            },
          });
        }
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  setSelection(sel: Selection) {
    switch (sel) {
      case Selection.Favourite:
        this.selectedGames = this.games.filter((g) => g.favourite);
        break;
      case Selection.Not_Completed:
        this.selectedGames = this.games.filter((g) => !g.completed);
        break;
      default:
        this.selectedGames = this.games;
    }
  }

  updateGame(game: UserGame): void {
    this.gameService.updateGame(game).subscribe({
      error: (err) => {
        console.error(err);
      },
    });
  }

  removeGame(game: UserGame): void {
    const id = game.game.id!;
    this.gameService.removeGame(id).subscribe({
      next: () => {
        this.games = this.games.filter((g) => g.game.id !== id);
        this.selectedGames = this.selectedGames.filter((g) => g.game.id !== id);
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
}
