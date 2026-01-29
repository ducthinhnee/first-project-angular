import { AsyncPipe, NgFor } from "@angular/common";
import { Component } from "@angular/core";
import { JobCardComponent } from "../../../../shared/components/job-card/job-card";
import { Job } from "../../../jobs/models/job.model";
import { JobsService } from "../../../jobs/services/jobs.service";
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { finalize, map, Observable } from "rxjs";


@Component({
  standalone: true,
  selector: 'app-featured-jobs',
  imports: [JobCardComponent, AsyncPipe, ProgressSpinnerModule],
  templateUrl: './featured-jobs.component.html'
})
export class FeaturedJobsComponent {
  jobs$!: Observable<Job[]>;
  loading = true;

  constructor(private jobsService: JobsService) {}

  ngOnInit(): void {
    this.loadJobs();
  }

  loadJobs(): void {
    this.jobs$ = this.jobsService
      .getFeaturedJobs(0, 9)
      .pipe(
        map(res => res.content),
        finalize(() => (this.loading = false))
      );
  }
}