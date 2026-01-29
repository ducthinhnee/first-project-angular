import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JobsService } from '../../services/jobs.service';
import { map, switchMap } from 'rxjs';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { AsyncPipe } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { DividerModule } from 'primeng/divider';

@Component({
  selector: 'app-job-detail',
  standalone: true,
  templateUrl: './job-detail.component.html',
  styleUrls: ['./job-detail.component.css'],
  imports: [CardModule, TagModule, AsyncPipe, ButtonModule, DividerModule],
})
export class JobDetailComponent {
  private route = inject(ActivatedRoute);
  private jobsService = inject(JobsService);

  job$ = this.route.paramMap.pipe(
    switchMap((params) => this.jobsService.getJobByIdMock(Number(params.get('id')))),
    map((job) => ({
      ...job,

      // 🔥 mock thêm nếu backend chưa có
      description: job.description || MOCK.description,
      benefits: MOCK.benefits,
      requirements: MOCK.requirements,
      tags: MOCK.tags,
      salaryText: `$${job.salaryMin} - $${job.salaryMax}`,
      locationText: job.location || 'Worldwide',
    }))
  );
}

const MOCK = {
  description: `
        We are looking for a Senior UX Designer to join our growing product team.
        You will work closely with product managers, engineers and stakeholders
        to design intuitive, user-centered digital experiences.
        `,
  requirements: [
    '3+ years experience in UX/UI design',
    'Strong portfolio with web & mobile projects',
    'Experience with Figma or similar tools',
    'Good communication skills',
  ],
  benefits: ['40k Salary', 'Async', 'Learning budget', 'Vision Insurance', 'Equity Compensation'],
  tags: ['Back-end', 'PHP', 'Laravel', 'Front-end'],
};
