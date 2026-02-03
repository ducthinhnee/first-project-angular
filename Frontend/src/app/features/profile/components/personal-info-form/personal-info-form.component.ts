import { Component, EventEmitter, inject, OnInit, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';

import { InputTextModule } from 'primeng/inputtext';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ButtonModule } from 'primeng/button';

import { AvatarUploadComponent } from '../avatar-upload/avatar-upload.component';
import { SkillService } from '../../services/skill.service';
import { Skill } from '../../models/skill.model';
import { CreateSkillDialogComponent } from '../create-skill-dialog/create-skill-dialog.component';
import { CandidateProfileService } from '../../services/candidate-profile.service';

interface AutoCompleteCompleteEvent {
  originalEvent: Event;
  query: string;
}

@Component({
  selector: 'app-personal-info-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    AutoCompleteModule,
    ButtonModule,
    AvatarUploadComponent,
    CreateSkillDialogComponent
  ],
  templateUrl: './personal-info-form.component.html',
})
export class PersonalInfoFormComponent implements OnInit {
  @Output() save = new EventEmitter<any>();
  successMessage = signal('');
  errorMessage = signal('');

  // form
  private fb = inject(FormBuilder);
  form = this.fb.group({
    fullName: [''],
    headline: [''],
    experience: [null],
    education: [[] as Skill[]],
    website: [''],
  });
  
  // skill
  private skillService = inject(SkillService);
  loadingSkills = signal(false);
  skills = signal<Skill[]>([]);
  filteredSkills = signal<Skill[]>([]);
  
  searchSkill(event: AutoCompleteCompleteEvent) {
    const query = event.query.toLowerCase();

    this.filteredSkills.set(
        this.skills().filter(skill =>
        skill.name.toLowerCase().includes(query)
        )
    );
  }

  ngOnInit() {
    this.loadSkills();
    this.loadProfile();
  }

  loadSkills() {
    this.loadingSkills.set(true);

    this.skillService.getSkills().subscribe({
        next: (data) => {
          this.skills.set(data);
          this.filteredSkills.set(data);
          this.loadingSkills.set(false);
        },
        error: (err) => {
          console.error(err);
          this.loadingSkills.set(false);
        }
    });
  }

  // profile
  private profileService = inject(CandidateProfileService);

  loadProfile() {
    this.profileService.getProfile().subscribe({
      next: (profile) => {
        this.form.patchValue({
          fullName: profile.fullName,
          headline: profile.phone,
          website: profile.summary,
          education: profile.skills   // map skills vào autocomplete
        });
      },
      error: () => {
        this.errorMessage.set('Failed to load profile');
      }
    });
  }

  submit() {
    if (this.form.invalid) return;

    const value = this.form.value;

    const payload = {
      fullName: value.fullName!,
      phone: value.headline!,
      summary: value.website!,
      skills: (value.education || []).map((s: Skill) => ({
        id: s.id
      }))
    };

    this.successMessage.set('');
    this.errorMessage.set('');

    this.profileService.updateProfile(payload).subscribe({
      next: () => {
        this.successMessage.set('Profile updated successfully');
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message || 'Update failed');
      }
    });
  }
}
