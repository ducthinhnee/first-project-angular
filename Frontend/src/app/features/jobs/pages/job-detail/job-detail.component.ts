import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JobsService } from '../../services/jobs.service';
import { finalize, map, Observable, switchMap, tap } from 'rxjs';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { AsyncPipe } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { DividerModule } from 'primeng/divider';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { Job } from '../../models/job.model';
import { DialogModule } from 'primeng/dialog';
import { FormsModule } from '@angular/forms';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { Resume } from '../../../profile/models/resume.model';
import { ResumeService } from '../../../profile/services/resume.service';
import { MessageModule } from 'primeng/message';
import { FileUploadModule } from 'primeng/fileupload';

@Component({
  selector: 'app-job-detail',
  standalone: true,
  templateUrl: './job-detail.component.html',
  styleUrls: ['./job-detail.component.css'],
  imports: [CardModule, TagModule, AsyncPipe, ButtonModule, DividerModule, ProgressSpinnerModule, 
    DialogModule,
    FormsModule,
    AutoCompleteModule,
    MessageModule,
    FileUploadModule
  ],
})
export class JobDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private jobsService = inject(JobsService);
  private resumeService = inject(ResumeService);

  // choose resume
  resumes = signal<Resume[]>([]);
  loading = signal(false);

  showApplyDialog = false;
  jobId!: number;

  filteredResumes: any[] = [];
  selectedResume: any = null;
  applyLoading = signal(false);

  filterResumes(event: any) {
    const query = event.query.toLowerCase();

    this.filteredResumes = this.resumes().filter(r =>
      this.getFileName(r.fileUrl).toLowerCase().includes(query)
    );
  }

  getFileName(url: string): string {
    return url.split('/').pop() || '';
  }

  job$ = this.route.paramMap.pipe(
    map(params => Number(params.get('id') ?? 0)),
    tap(id => this.jobId = id),
    switchMap(id => this.jobsService.getJobByIdApi(id)),
    map((job) => ({
      ...job,
      description: job.description || MOCK.description,
      benefits: MOCK.benefits,
      requirements: MOCK.requirements,
      tags: MOCK.tags,
      salaryText: `$${job.salaryMin} - $${job.salaryMax}`,
      locationText: job.location || 'Worldwide',
    }))
  );

  openApplyDialog() {
    this.showApplyDialog = true;
  }

  loadResumes() {
    this.loading.set(true);

    this.resumeService.getAll().subscribe({
      next: (data) => {
        this.resumes.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
      }
    });
  }

  ngOnInit(): void {
    this.loadResumes();
  }

  errorMsg = signal<string | null>(null);
  successMsg = signal<string | null>(null);

  // upload resume
  onUpload(event: any) {
    const file: File = event.files?.[0];
    if (!file) return;

    this.loading.set(true);
    this.successMsg.set('');
    this.errorMsg.set('');

    this.resumeService.upload(file).subscribe({
      next: (resume) => {
        const mapped = {
          ...resume,
          fileName: resume.fileUrl.split('/').pop()
        };

        // add vào list đầu tiên
        this.resumes.update(list => [mapped, ...list]);
        this.selectedResume = mapped;
        this.successMsg.set('Upload CV successfully');
        this.loading.set(false);
      },
      error: (err) => {
        this.errorMsg.set(err.error?.message || 'Upload failed');
        this.loading.set(false);
      }
    });
  }


  // apply job
  applyJob(jobId: number) {
    this.errorMsg.set(null);
    this.successMsg.set(null);
    if (!this.selectedResume) return;

    const payload = {
      jobId,
      resumeId: this.selectedResume.id
    };

    this.applyLoading.set(true);

    this.jobsService.applyJob(payload).subscribe({
      next: () => {
        this.applyLoading.set(false);
        this.showApplyDialog = false;
        this.errorMsg.set(null);
        this.successMsg.set('Apply successfully!');
      },
      error: (err) => {
        console.error(err);
        this.applyLoading.set(false);
        this.errorMsg.set(err.error?.message || 'Apply failed');
      }
    });
  }
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
