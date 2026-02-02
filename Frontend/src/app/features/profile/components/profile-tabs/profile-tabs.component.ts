import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TabsModule } from 'primeng/tabs';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-profile-tabs',
  standalone: true,
  imports: [CommonModule, TabsModule, ButtonModule],
  templateUrl: './profile-tabs.component.html',
})
export class ProfileTabsComponent {
  value = 0;
}
