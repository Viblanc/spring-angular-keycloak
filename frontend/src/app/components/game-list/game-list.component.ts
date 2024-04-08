import { Component, OnInit } from '@angular/core';
import { GameService } from '../../services/game.service';
import { CommonModule } from '@angular/common';
import { UserGame } from '../../models/user-game.model';
import { GameComponent } from '../game/game.component';

@Component({
  selector: 'app-game-list',
  standalone: true,
  imports: [CommonModule, GameComponent],
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
}
