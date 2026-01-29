import { Component } from "@angular/core";
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  standalone: true,
  selector: 'app-header',
  imports: [MenubarModule, ButtonModule, InputTextModule],
  templateUrl: './header.component.html'
})
export class HeaderComponent {
  items = [
    { label: 'Home', routerLink: '/' },
    { label: 'Find Job', routerLink: '/jobs' },
    { label: 'Employers' },
    { label: 'Candidates' },
    { label: 'Pricing Plans' },
    { label: 'Customer Supports' }
  ];
}