import { computed, Injectable, signal } from "@angular/core";
import { Job } from "../../features/jobs/models/job.model";
import { CompanyJobsService } from "../../features/companies/services/company.service";

@Injectable({ providedIn: 'root' })
export class CompanyJobsStore {
  private readonly _jobs = signal<Job[]>([]);
  private readonly _loading = signal(false);
  private readonly _error = signal<string | null>(null);

  totalApplicants = computed(() =>
    this.jobs().reduce((sum, j) => sum + (j.totalApplicants ?? 0), 0)
  );

  jobs = this._jobs.asReadonly();
  loading = this._loading.asReadonly();
  error = this._error.asReadonly();

  constructor(private jobService: CompanyJobsService) {}

  loadJobs() {
    this._loading.set(true);
    this._error.set(null);

    this.jobService.getOverviewJobs().subscribe({
      next: res => {
        this._jobs.set(res);
        this._loading.set(false);
      },
      error: err => {
        this._loading.set(false);
        this._error.set('Failed to load jobs');
      }
    });
  }

  createJob(payload: any) {
    this._loading.set(true);
    this._error.set(null);

    this.jobService.createCompanyJob(payload).subscribe({
        next: (createdJob) => {
            this._loading.set(false);
            this._jobs.update((list) => [createdJob, ...list]);
        },
        error: err => {
            this._loading.set(false);
            this._error.set(err?.error?.message || 'Create job failed');
        }
    });
  }
}
