import { Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { GameService } from '../../services/game.service';
import { NgFor } from '@angular/common';
import { UserGame } from '../../models/user-game.model';
import { FormsModule } from '@angular/forms';
import { GameComponent } from '../game/game.component';

@Pipe({
  name: 'rangeNumber',
  standalone: true,
})
export class RangeNumber implements PipeTransform {
  transform(length: number) {
    return Array.from({ length: length + 1 }, (_, k) => k);
  }
}

@Component({
  selector: 'app-game-list',
  standalone: true,
  imports: [NgFor, FormsModule, GameComponent, RangeNumber],
  templateUrl: './game-list.component.html',
  styleUrl: './game-list.component.css',
})
export class GameListComponent implements OnInit {
  games: UserGame[] = [];
  constructor(private gameService: GameService) {}

  ngOnInit(): void {
    this.gameService.getGames().subscribe({
      next: (games) => {
        this.games = games;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  updateGame(game: UserGame): void {
    this.gameService.updateGame(game).subscribe({
      error: (err) => {
        console.error(err);
      },
    });
  }
}
