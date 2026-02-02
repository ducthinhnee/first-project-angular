import { Component, computed, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule } from 'primeng/button';
import { AvatarModule } from 'primeng/avatar';
import { MenuModule } from 'primeng/menu';
import { AuthStorageService } from '../../services/auth-storage.service';

@Component({
  standalone: true,
  selector: 'app-header',
  imports: [
    MenubarModule,
    ButtonModule,
    RouterModule,
    AvatarModule,
    MenuModule
  ],
  templateUrl: './header.component.html'
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
      label: this.user()?.role || 'ROLE',
      icon: 'pi pi-user',
      disabled: true
    },
    {
      label: 'Profile',
      icon: 'pi pi-user'
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
