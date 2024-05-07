import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Subject, debounceTime, distinctUntilChanged, switchMap } from 'rxjs';
import { UserService } from '../../services/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent implements OnInit {
  username = '';
  isLoading = false;
  users: string[] = [];
  private username$ = new Subject<string>();
  constructor(
    protected readonly authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.username$
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        switchMap((username) => {
          this.isLoading = true;
          if (username.trim().length > 2)
            return this.userService.search(username);
          else return [];
        })
      )
      .subscribe({
        next: (users) => {
          this.users = users;
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  search(): void {
    this.username$.next(this.username);
  }
}
