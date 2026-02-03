import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { Skill } from '../models/skill.model';
import { ExperiencePayload } from '../models/experience.model';

@Injectable({ providedIn: 'root' })
export class ExperienceService {
  private readonly BASE_URL = '/api/v1/experiences';

  constructor(private api: ApiService) {}

  createExperience(payload: ExperiencePayload) {
    return this.api.post<ExperiencePayload>(`${this.BASE_URL}/candidate-profile`, payload);
  }

  getMyExperiences() {
    return this.api.get<ExperiencePayload[]>(this.BASE_URL);
  }

  deleteExperience(id: number) {
    return this.api.delete<void>(`${this.BASE_URL}/${id}`);
  }
}