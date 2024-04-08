import { Component, OnInit } from '@angular/core';
import { GameService } from '../../services/game.service';
import { CommonModule } from '@angular/common';
import { UserGame } from '../../models/user-game.model';

@Component({
  selector: 'app-game-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game-list.component.html',
  styleUrl: './game-list.component.css',
})
export class GameListComponent implements OnInit {
  games: UserGame[] = [];
  constructor(private gameService: GameService) {}

  ngOnInit(): void {
    this.gameService.getGames().subscribe({
      next: (games) => {
        console.log(games);
        this.games = games;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
}
