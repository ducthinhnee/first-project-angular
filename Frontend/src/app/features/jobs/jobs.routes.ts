import { Routes } from '@angular/router';
import { JobDetailComponent } from './pages/job-detail/job-detail.component';

export const jobsRoutes: Routes = [
  {
    path: ':id',
    component: JobDetailComponent,
  },
];
