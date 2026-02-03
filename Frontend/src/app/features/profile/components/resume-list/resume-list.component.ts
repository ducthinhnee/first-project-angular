import { Component, EventEmitter, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ButtonModule } from 'primeng/button';
import { FileUploadModule } from 'primeng/fileupload';
import { ResumeItemComponent } from '../resume-item/resume-item.component';
import { Resume } from '../../models/resume.model';
import { ResumeService } from '../../services/resume.service';

@Component({
  selector: 'app-resume-list',
  standalone: true,
  imports: [
    CommonModule,
    ButtonModule,
    FileUploadModule,
    ResumeItemComponent,
  ],
  templateUrl: './resume-list.component.html',
})
export class ResumeListComponent {
  @Output() upload = new EventEmitter<File>();
  @Output() delete = new EventEmitter<number>();

  resumes = signal<Resume[]>([]);
  loading = signal(false);
  successMessage = signal('');
  errorMessage = signal('');

  constructor(private resumeService: ResumeService) {
    this.loadResumes();
  }

  loadResumes() {
    this.loading.set(true);
    this.errorMessage.set('');

    this.resumeService.getAll().subscribe({
      next: (data) => {
        this.resumes.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('Failed to load resumes');
        this.loading.set(false);
      }
    });
  }

  // ===== UPLOAD =====
  onUpload(event: any) {
    const file: File = event.files?.[0];
    if (!file) return;

    this.loading.set(true);
    this.successMessage.set('');
    this.errorMessage.set('');

    this.resumeService.upload(file).subscribe({
      next: (resume) => {
        this.resumes.update(list => [resume, ...list]);
        this.successMessage.set('Upload CV successfully');
        this.loading.set(false);
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message || 'Upload failed');
        this.loading.set(false);
      }
    });
  }

  // ===== DELETE =====
  onDelete(resumeId: number) {
    this.loading.set(true);
    this.successMessage.set('');
    this.errorMessage.set('');

    this.resumeService.delete(resumeId).subscribe({
      next: () => {
        this.resumes.update(list =>
          list.filter(r => r.id !== resumeId)
        );
        this.successMessage.set('Resume deleted');
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('Delete failed');
        this.loading.set(false);
      }
    });
  }
}
