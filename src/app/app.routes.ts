import { Routes } from '@angular/router';
import { HomeComponent } from './home/home-component';
import { UsersComponent } from './user/user-component';
import { UserDetailComponent } from './user/user-detail-component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'users',
    component: UsersComponent,
    children: [{ path: ':id', component: UserDetailComponent }],
  },
];
