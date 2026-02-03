import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CompanyJob } from '../models/company-job.model';

@Injectable({ providedIn: 'root' })
export class CompanyJobsService {
  getOverviewJobs(): Observable<CompanyJob[]> {
    return of([
      {
        id: 1,
        title: 'UI/UX Designer',
        type: 'Full Time',
        remainingDays: 27,
        status: 'ACTIVE',
        applications: 798,
      },
      {
        id: 2,
        title: 'Senior UX Designer',
        type: 'Internship',
        remainingDays: 8,
        status: 'ACTIVE',
        applications: 185,
      },
      {
        id: 3,
        title: 'Technical Support Specialist',
        type: 'Part Time',
        remainingDays: 4,
        status: 'ACTIVE',
        applications: 556,
      },
      {
        id: 4,
        title: 'Front End Developer',
        type: 'Full Time',
        remainingDays: 0,
        status: 'EXPIRED',
        applications: 740,
      },
    ]);
  }
}
