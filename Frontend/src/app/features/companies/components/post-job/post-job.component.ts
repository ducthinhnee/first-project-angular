import { Component, OnInit } from '@angular/core';
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
  jobForm!: FormGroup;

  jobRoles = ['Frontend', 'Backend', 'Fullstack'];
  salaryTypes = ['Monthly', 'Yearly'];
  jobTypes = ['Full-time', 'Part-time', 'Remote'];
  jobLevels = ['Junior', 'Mid', 'Senior'];

  benefits = [
    '401k Salary',
    'Distributed Team',
    'Medical Insurance',
    'Unlimited Vacation',
    'Company Retreats',
  ];

  filteredRoles: string[] = [];
  filteredSalaryTypes: string[] = [];
  filteredJobTypes: string[] = [];
  filteredJobLevels: string[] = [];

  filter(event: any, source: string[]) {
    const query = event.query.toLowerCase();
    return source.filter((item) => item.toLowerCase().includes(query));
  }

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.jobForm = this.fb.group({
      title: ['', Validators.required],
      tags: [''],
      role: [null],
      minSalary: [],
      maxSalary: [],
      salaryType: [null],

      education: [null],
      experience: [null],
      jobType: [null],
      vacancies: [],
      expirationDate: [],
      jobLevel: [null],

      country: [null],
      city: [null],
      remote: [false],

      benefits: [[]],
      description: [''],
    });
  }

  submit() {
    if (this.jobForm.invalid) {
      this.jobForm.markAllAsTouched();
      return;
    }

    console.log(this.jobForm.value);
  }
}
