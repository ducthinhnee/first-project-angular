import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-user-detail',
  template: `
    <h3>User Detail</h3>

    @switch (userId) { @case ('1') {
    <p>User Admin</p>
    } @case ('2') {
    <p>User Manager</p>
    } @default {
    <p>User Guest</p>
    } }
  `,
})
export class UserDetailComponent {
  userId: string | null;

  constructor(route: ActivatedRoute) {
    this.userId = route.snapshot.paramMap.get('id');
  }
}
