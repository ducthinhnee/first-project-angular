import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';

import { ProfileTabsComponent } from '../../components/profile-tabs/profile-tabs.component';
import { PersonalInfoFormComponent } from '../../components/personal-info-form/personal-info-form.component';
import { ResumeListComponent } from '../../components/resume-list/resume-list.component';
import { ExperienceFormComponent } from '../../components/experience-form/experience-form.component';

@Component({
  selector: 'app-profile-settings',
  standalone: true,
  imports: [
    CommonModule,
    CardModule,
    ProfileTabsComponent,
    PersonalInfoFormComponent,
    ResumeListComponent,
    ExperienceFormComponent,
  ],
  templateUrl: './profile-settings.component.html',
})
export class ProfileSettingsComponent {
  saveProfile(data: any) {
    console.log('SAVE PROFILE', data);
  }

  uploadResume(file: File) {
    console.log('UPLOAD', file);
  }

  deleteResume(id: number) {
    console.log('DELETE', id);
  }
}
