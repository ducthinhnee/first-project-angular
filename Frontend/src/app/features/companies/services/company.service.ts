import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CompanyJob } from '../models/company-job.model';
import { ApiService } from '../../../core/services/api.service';
import { Job } from '../../jobs/models/job.model';
import { Applicant } from '../models/applicant.model';

@Injectable({ providedIn: 'root' })
export class CompanyJobsService {
  private readonly BASE_URL = '/api/v1/jobs';

  constructor(private api: ApiService) {}

  getOverviewJobs(): Observable<Job[]> {
    return this.api.get<Job[]>(`${this.BASE_URL}/my-company`);
  }

  createCompanyJob(payload: Job) {
    return this.api.post<Job>(`${this.BASE_URL}/my-company`, payload);
  }

  getApplicants(jobId: number) {
    return this.api.get<Applicant[]>(
      `http://localhost:4200/api/v1/company/jobs/${jobId}/applicants`
    );
  }
}
