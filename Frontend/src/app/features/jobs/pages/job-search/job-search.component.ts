import { Component, OnInit, effect, inject, signal } from '@angular/core';
import { JobsService } from '../../services/jobs.service';
import { Job } from '../../models/job.model';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { NgFor, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { JobCardComponent } from '../../../../shared/components/job-card/job-card';
import { PaginatorModule } from 'primeng/paginator';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
  selector: 'app-job-search',
  templateUrl: './job-search.component.html',
  styleUrls: ['./job-search.component.css'],
  imports: [
    InputTextModule,
    ButtonModule,
    PaginatorModule,
    FormsModule,
    JobCardComponent,
    AutoCompleteModule,
    ProgressSpinnerModule,
    NgClass
],
})
export class JobSearchComponent {
  jobs = signal<Job[]>([]);
  loading = signal(false);

  page = signal(0);
  size = 9;
  totalPages = signal(0);

  draftFilters = signal({
    keyword: '',
    location: '',
    jobType: null as string | null,
    level: null as string | null,
    minSalary: null as number | null,
    maxSalary: null as number | null,
  });

  appliedFilters = signal(this.draftFilters());

  // dropdown options
  jobTypes = [
    { label: 'Full time', value: 'FULL_TIME' },
    { label: 'Part time', value: 'PART_TIME' },
    { label: 'Remote', value: 'REMOTE' },
  ];

  levels = [
    { label: 'Junior', value: 'JUNIOR' },
    { label: 'Middle', value: 'MIDDLE' },
    { label: 'Senior', value: 'SENIOR' },
  ];

  filteredJobTypes: any[] = [];
  filteredLevels: any[] = [];

  searchJobType(event: any) {
    const query = event.query.toLowerCase();
    this.filteredJobTypes = this.jobTypes.filter((j) => j.label.toLowerCase().includes(query));
  }

  searchLevel(event: any) {
    const query = event.query.toLowerCase();
    this.filteredLevels = this.levels.filter((l) => l.label.toLowerCase().includes(query));
  }

  constructor(private jobsService: JobsService) {
    effect(() => {
      this.loadJobs();
    });
  }

  loadJobs() {
    this.loading.set(true);

    this.jobsService
    //   .searchJobs({
    //     ...this.draftFilters,
    //     page: this.page(),
    //     size: this.size,
    //   })
      .searchJobs({
        keyword: this.draftFilters().keyword,
        location: this.draftFilters().location,
        jobType: this.draftFilters().jobType ?? undefined,
        level: this.draftFilters().level ?? undefined,
        minSalary: this.draftFilters().minSalary ?? undefined,
        maxSalary: this.draftFilters().maxSalary ?? undefined,
        page: this.page(),
        size: this.size,
      })
      .subscribe({
        next: (res) => {
          this.jobs.set(res.content);
          this.totalPages.set(res.totalPages);
          this.loading.set(false);
        },
        error: () => this.loading.set(false),
      });
  }

  onSearch() {
    this.page.set(0);
    console.log(this.draftFilters());
    this.appliedFilters.set(this.draftFilters());
    this.loadJobs();
  }

  changePage(page?: number) {
    if (page === undefined) return;
    this.page.set(page);
  }

  resetFilters() {
    const empty = {
      keyword: '',
      location: '',
      jobType: null,
      level: null,
      minSalary: null,
      maxSalary: null,
    };

    this.draftFilters.set(empty);
    this.appliedFilters.set(empty);
    this.page.set(0);
  }

  //   updateDraft<K extends keyof typeof this.draftFilters extends infer T ? T : never>(
  //     key: K,
  //     value: any
  //   ) {
  //     this.draftFilters.update(f => ({ ...f, [key]: value }));
  //   }

  updateDraft(key: string, value: any) {
    this.draftFilters.update((f) => ({
      ...f,
      [key]: value,
    }));
  }
}
