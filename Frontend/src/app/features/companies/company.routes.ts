import { Routes } from '@angular/router';
import { EmployerDashboardComponent } from './pages/employer-dashboard.component';
import { JobApplicationsComponent } from './components/job-applications/job-applications.component';
import { PostJobComponent } from './components/post-job/post-job.component';

export const COMPANIES_ROUTES: Routes = [
  {
    path: '',
    component: EmployerDashboardComponent,
  },
  {
    path: 'jobs/:jobId/applications',
    component: JobApplicationsComponent,
  },
  {
    path: 'post-job',
    component: PostJobComponent,
  },
];
