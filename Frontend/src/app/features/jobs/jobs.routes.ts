import { Routes } from '@angular/router';
import { JobDetailComponent } from './pages/job-detail/job-detail.component';
import { JobSearchComponent } from './pages/job-search/job-search.component';

export const jobsRoutes: Routes = [
  {
    path: '',
    component: JobSearchComponent,
  },
  {
    path: ':id',
    component: JobDetailComponent,
  },
];
