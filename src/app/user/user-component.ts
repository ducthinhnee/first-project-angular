import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { ShortenPipe } from '../pipes/shorten.pipe';

@Component({
  standalone: true,
  selector: 'app-users',
  imports: [RouterOutlet, RouterLink, ShortenPipe],
  template: `
    <h2>Users</h2>

    @if (users.length === 0) {
    <p>Không có user</p>
    } @else {
    <ul>
      @for (user of users; track user.id) {
      <li>
        <a [routerLink]="[user.id]">
          {{ user.name | shorten : 10 }}
        </a>
      </li>
      }
    </ul>
    }

    <router-outlet />
  `,
})
export class UsersComponent {
  users = [
    { id: 1, name: 'Nguyễn Văn A' },
    { id: 2, name: 'Trần Thị Bbbbbbbb' },
    { id: 3, name: 'Lê Văn C' },
  ];
}
