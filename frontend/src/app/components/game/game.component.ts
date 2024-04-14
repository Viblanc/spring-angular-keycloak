import { Component, Input } from '@angular/core';
import { DatePipe, NgFor } from '@angular/common';
import { Game } from '../../models/game.model';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [NgFor, DatePipe],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css',
})
export class GameComponent {
  @Input() game!: Game;
}
