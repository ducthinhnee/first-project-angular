import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { CheckboxModule } from 'primeng/checkbox';
import { MultiSelectModule } from 'primeng/multiselect';
import { ButtonModule } from 'primeng/button';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { DatePickerModule } from 'primeng/datepicker';
import { Job } from '../../../jobs/models/job.model';
import { CompanyJobsService } from '../../services/company.service';
import { CompanyJobsStore } from '../../../../core/stores/company-jobs.store';

@Component({
  selector: 'app-post-job',
  templateUrl: './post-job.component.html',
  imports: [
    ReactiveFormsModule,
    CardModule,
    InputTextModule,
    AutoCompleteModule,
    InputNumberModule,
    DatePickerModule,
    CheckboxModule,
    MultiSelectModule,
    ButtonModule,
  ],
})
export class PostJobComponent implements OnInit {
  private fb = inject(FormBuilder);
  private store = inject(CompanyJobsStore);

  jobForm!: FormGroup;

  jobTypes = ['FULL_TIME', 'PART_TIME', 'INTERN', 'CONTRACT'];
  jobLevels = ['JUNIOR', 'MIDDLE', 'SENIOR'];

  filteredJobTypes: string[] = [];
  filteredJobLevels: string[] = [];

  loading = signal(false);
  error = signal<string | null>(null);

  filter(event: any, source: string[]) {
    const query = event.query.toLowerCase();
    return source.filter((item) => item.toLowerCase().includes(query));
  }

  ngOnInit() {
    this.jobForm = this.fb.group({
      title: ['', Validators.required],
      location: ['', Validators.required],
      minSalary: ['', Validators.required],
      maxSalary: ['', Validators.required],
      jobType: ['', Validators.required],
      jobLevel: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  submit() {
    if (this.jobForm.invalid) {
      this.jobForm.markAllAsTouched();
      return;
    }

    const value = this.jobForm.value;
    const payload: Job = {
      id: 0,

      title: value.title!,
      companyName: value.companyName!,
      location: value.location!,
      description: value.description || undefined,

      salaryMin: Number(value.salaryMin),
      salaryMax: Number(value.salaryMax),

      jobType: value.jobType as Job['jobType'],
      level: value.level as Job['level'],
      status: 'OPEN',

      totalApplicants: value.totalApplicants ?? 0
    };

    this.store.createJob(payload);

  }
}
