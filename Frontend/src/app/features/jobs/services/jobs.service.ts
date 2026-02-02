import { Injectable } from '@angular/core';
import { delay, Observable, of } from 'rxjs';
import { Job } from '../models/job.model';
import { ApiService } from '../../../core/services/api.service';
import { PageResponse } from '../../../shared/models/page-response.model';
import { JobSearchParams } from '../../../shared/models/jobsearch-params.model';

@Injectable({ providedIn: 'root' })
export class JobsService {
  private readonly BASE_URL = '/api/v1/jobs/';

  constructor(private api: ApiService) {}

  getFeaturedJobs(page = 0, size = 10): Observable<PageResponse<Job>> {
    return this.api.get<PageResponse<Job>>(this.BASE_URL, {
      page,
      size,
    });
  }

  getJobByIdApi(id: number): Observable<Job> {
    return this.api.get<Job>(`${this.BASE_URL}${id}`);
  }

  getJobByIdMock(id: number): Observable<Job> {
    const mockJobs: Job[] = [
      {
        id: 1,
        title: 'Java Backend Developer',
        companyName: 'Tech Solutions',
        location: 'Ho Chi Minh',
        salaryMin: 1500,
        salaryMax: 2500,
        jobType: 'FULL_TIME',
        level: 'MIDDLE',
        status: 'OPEN',
        description: 'Build scalable backend systems using Spring Boot.',
      },
      {
        id: 2,
        title: 'Frontend Angular Developer',
        companyName: 'Digital Corp',
        location: 'Ha Noi',
        salaryMin: 1200,
        salaryMax: 2200,
        jobType: 'FULL_TIME',
        level: 'SENIOR',
        status: 'OPEN',
        description: 'Develop UI using Angular and PrimeNG.',
      },
    ];

    const job = mockJobs.find((j) => j.id === id)!;

    return of(job).pipe(delay(500));
  }

  searchJobs(params: JobSearchParams): Observable<PageResponse<Job>> {
    const queryParams: any = {
      page: params.page ?? 0,
      size: params.size ?? 10
    };

    if (params.keyword) queryParams.keyword = params.keyword;
    if (params.location) queryParams.location = params.location;
    if (params.jobType) queryParams.jobType = params.jobType;
    if (params.level) queryParams.level = params.level;
    if (params.minSalary != null) queryParams.minSalary = params.minSalary;
    if (params.maxSalary != null) queryParams.maxSalary = params.maxSalary;

    return this.api.get<PageResponse<Job>>(
      `${this.BASE_URL}`,
      queryParams
    );
  }
}
