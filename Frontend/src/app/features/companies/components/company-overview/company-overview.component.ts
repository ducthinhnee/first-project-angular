import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { MenuModule } from 'primeng/menu';
import { CompanyJob } from '../../models/company-job.model';
import { CompanyJobsService } from '../../services/company.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-company-overview',
  imports: [CommonModule, TableModule, ButtonModule, MenuModule],
  templateUrl: './company-overview.component.html',
})
export class CompanyOverviewComponent implements OnInit {
  jobs = signal<CompanyJob[]>([]);

  menuItems = [
    { label: 'Promote Job', icon: 'pi pi-megaphone' },
    { label: 'View Detail', icon: 'pi pi-eye' },
    { label: 'Mark as expired', icon: 'pi pi-times-circle' },
  ];

  constructor(private jobService: CompanyJobsService, private router: Router) {}

  ngOnInit() {
    this.jobService.getOverviewJobs().subscribe((res) => {
      this.jobs.set(res);
    });
  }

  goToJob(jobId: number) {
    this.router.navigate(['/company/jobs', jobId, 'applications']);
  }
}
