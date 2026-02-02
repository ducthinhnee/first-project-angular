import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { Job } from '../../../features/jobs/models/job.model';
import { SalaryPipe } from '../../pipes/salary.pipe';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-job-card',
  imports: [
    CommonModule,
    CardModule,
    ButtonModule,
    TagModule,
    SalaryPipe
  ],
  templateUrl: './job-card.html',
  styleUrls: ['./job-card.css']
})
export class JobCardComponent {
  @Input() job!: Job;

  constructor(private router: Router) {}
  goToDetail() {
    this.router.navigate(['/jobs', this.job.id]);
  }
}