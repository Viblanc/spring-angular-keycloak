import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl: String;
  constructor(private http: HttpClient) {
    this.apiUrl = environment.apiUrl;
  }

  search(username: string): Observable<string[]> {
    return this.http.get<string[]>(
      `${this.apiUrl}/users/search?username=${username}`
    );
  }
}
