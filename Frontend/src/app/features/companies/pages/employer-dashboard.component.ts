import { Component } from '@angular/core';
import { TabsModule } from 'primeng/tabs';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';
import { CompanyOverviewComponent } from '../components/company-overview/company-overview.component';
import { PostJobComponent } from '../components/post-job/post-job.component';

@Component({
  standalone: true,
  selector: 'app-employer-dashboard',
  imports: [CommonModule, TabsModule, ButtonModule, CompanyOverviewComponent, PostJobComponent],
  templateUrl: './employer-dashboard.component.html',
})
export class EmployerDashboardComponent {
  value = 0; // tab đang active
}
