import { Game } from './game.model';

export class UserGame {
  game!: Game;
  completed?: boolean;
  favourite?: boolean;
  rating?: number;
}
