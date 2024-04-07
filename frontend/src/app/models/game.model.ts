import { Platform } from './platform.model';

export class Game {
  id?: number;
  name?: string;
  releaseDate?: Date;
  coverUrl?: string;
  summary?: string;
  platforms?: Platform[];
}
