import { Component, computed, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule } from 'primeng/button';
import { AvatarModule } from 'primeng/avatar';
import { MenuModule } from 'primeng/menu';
import { AuthStorageService } from '../../services/auth-storage.service';

import { InputTextModule } from "primeng/inputtext";

@Component({
  standalone: true,
  selector: 'app-header',
  imports: [
    MenubarModule,
    ButtonModule,
    RouterModule,
    AvatarModule,
    MenuModule,
    InputTextModule
  ],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  private authStorage = inject(AuthStorageService);
  private router = inject(Router);

  user = this.authStorage.user;

  items = [
    { label: 'Home', routerLink: '/' },
    { label: 'Find Job', routerLink: '/jobs' }
    // { label: 'Employers' },
    // { label: 'Candidates' },
    // { label: 'Pricing Plans' },
    // { label: 'Customer Supports' }
  ];

  userMenu = [
    {
      label: 'Role: ' + this.user()?.role || 'ROLE',
      icon: 'pi pi-cog',
      disabled: true
    },
    {
      label: 'Profile',
      icon: 'pi pi-user',
      command: () => {
        if(this.user()?.role == 'CANDIDATE'){
          this.router.navigate(['/profile']);
        } else if (this.user()?.role == 'EMPLOYER'){
          this.router.navigate(['/company']);
        }
      }
    },
    {
      label: 'Logout',
      icon: 'pi pi-sign-out',
      command: () => this.logout()
    }
  ];

  logout() {
    this.authStorage.clear();
    this.router.navigate(['/auth/login']);
  }
}
