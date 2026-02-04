import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { MenuModule } from 'primeng/menu';
import { TagModule } from 'primeng/tag';
import { Applicant } from '../../models/applicant.model';
import { ActivatedRoute } from '@angular/router';
import { CompanyJobsService } from '../../services/company.service';

@Component({
  standalone: true,
  selector: 'app-job-applications',
  imports: [CommonModule, CardModule, ButtonModule, MenuModule, TagModule],
  templateUrl: './job-applications.component.html',
})
export class JobApplicationsComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private jobService = inject(CompanyJobsService);

  applicants = signal<Applicant[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);

  ngOnInit() {
    const jobId = Number(this.route.snapshot.paramMap.get('jobId'));

    if (!jobId) return;

    this.loadApplicants(jobId);
  }

  loadApplicants(jobId: number) {
    this.loading.set(true);

    this.jobService.getApplicants(jobId).subscribe({
      next: (res) => {
        const mapped = res.map((app) => ({
          ...app,
          skills: app.skills?.map((s: any) => s.name) ?? [],
        }));

        this.applicants.set(mapped);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Failed to load applicants');
        this.loading.set(false);
      },
    });
  }

  downloadCV(url: string) {
    const fileName = url.split('/').pop() || '';
    window.open(`http://localhost:8080/api/v1/resumes/download/${fileName}`, '_blank');
  }

  // Dummy data for UI representation
  allApplications = [
    {
      name: 'Ronald Richards',
      title: 'UI/UX Designer',
      experience: '7 Years',
      education: 'Master Degree',
      appliedAt: 'Jan 23, 2022',
      avatar: 'https://i.pravatar.cc/150?img=3',
    },
    {
      name: 'Theresa Webb',
      title: 'Product Designer',
      experience: '7 Years',
      education: 'High School Degree',
      appliedAt: 'Jan 23, 2022',
      avatar: 'https://i.pravatar.cc/150?img=5',
    },
  ];

  menuItems = [
    { label: 'Edit Column', icon: 'pi pi-pencil' },
    { label: 'Delete', icon: 'pi pi-trash', severity: 'danger' },
  ];
}
