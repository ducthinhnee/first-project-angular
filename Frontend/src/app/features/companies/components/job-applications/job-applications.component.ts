import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { MenuModule } from 'primeng/menu';
import { TagModule } from 'primeng/tag';

@Component({
  standalone: true,
  selector: 'app-job-applications',
  imports: [CommonModule, CardModule, ButtonModule, MenuModule, TagModule],
  templateUrl: './job-applications.component.html',
})
export class JobApplicationsComponent {
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
    {
      name: 'Theresa Webb',
      title: 'Product Designer',
      experience: '7 Years',
      education: 'High School Degree',
      appliedAt: 'Jan 23, 2022',
      avatar: 'https://i.pravatar.cc/150?img=5',
    },
    {
      name: 'Theresa Webb',
      title: 'Product Designer',
      experience: '7 Years',
      education: 'High School Degree',
      appliedAt: 'Jan 23, 2022',
      avatar: 'https://i.pravatar.cc/150?img=5',
    },
    {
      name: 'Theresa Webb',
      title: 'Product Designer',
      experience: '7 Years',
      education: 'High School Degree',
      appliedAt: 'Jan 23, 2022',
      avatar: 'https://i.pravatar.cc/150?img=5',
    },
    {
      name: 'Theresa Webb',
      title: 'Product Designer',
      experience: '7 Years',
      education: 'High School Degree',
      appliedAt: 'Jan 23, 2022',
      avatar: 'https://i.pravatar.cc/150?img=5',
    },
    {
      name: 'Theresa Webb',
      title: 'Product Designer',
      experience: '7 Years',
      education: 'High School Degree',
      appliedAt: 'Jan 23, 2022',
      avatar: 'https://i.pravatar.cc/150?img=5',
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

  shortlisted = [
    {
      name: 'Jenny Wilson',
      title: 'UI Designer',
      experience: '7 Years',
      education: 'Bachelor Degree',
      appliedAt: 'Jan 23, 2022',
      avatar: 'https://i.pravatar.cc/150?img=8',
    },
  ];

  menuItems = [
    { label: 'Edit Column', icon: 'pi pi-pencil' },
    { label: 'Delete', icon: 'pi pi-trash', severity: 'danger' },
  ];
}
