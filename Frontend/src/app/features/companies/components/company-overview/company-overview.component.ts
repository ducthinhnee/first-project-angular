import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { MenuModule } from 'primeng/menu';
import { CompanyJob } from '../../models/company-job.model';
import { CompanyJobsService } from '../../services/company.service';
import { Router } from '@angular/router';
import { Job } from '../../../jobs/models/job.model';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { CompanyJobsStore } from '../../../../core/stores/company-jobs.store';

@Component({
  standalone: true,
  selector: 'app-company-overview',
  imports: [CommonModule, TableModule, ButtonModule, MenuModule, ProgressSpinnerModule],
  templateUrl: './company-overview.component.html',
})
export class CompanyOverviewComponent implements OnInit {
  private store = inject(CompanyJobsStore);
  private router = inject(Router);

  jobs = this.store.jobs;
  loading = this.store.loading;
  error = this.store.error;
  totalApplicants = this.store.totalApplicants;

  menuItems = [
    { label: 'Promote Job', icon: 'pi pi-megaphone' },
    { label: 'View Detail', icon: 'pi pi-eye' },
    { label: 'Mark as expired', icon: 'pi pi-times-circle' },
  ];

  ngOnInit() {
    this.store.loadJobs();
  }

  goToJob(jobId: number) {
    this.router.navigate(['/company/jobs', jobId, 'applications']);
  }
}
