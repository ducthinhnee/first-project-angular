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

  // form
  private fb = inject(FormBuilder);
  form = this.fb.group({
    fullName: [''],
    headline: [''],
    experience: [null],
    education: [null],
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

  // experienece
  experienceOptions = ['Junior', 'Mid', 'Senior'];
  filteredExperiences: string[] = [];

  searchExperience(event: AutoCompleteCompleteEvent) {
    this.filteredExperiences = this.experienceOptions.filter(opt =>
      opt.toLowerCase().includes(event.query.toLowerCase())
    );
  }


  submit() {
    this.save.emit(this.form.value);
  }
}
