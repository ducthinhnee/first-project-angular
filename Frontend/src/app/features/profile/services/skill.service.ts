import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { Skill } from '../models/skill.model';

@Injectable({ providedIn: 'root' })
export class SkillService {
    private readonly BASE_URL = '/api/v1/skills';

    constructor(private api: ApiService) {}

    getSkills(): Observable<Skill[]> {
        return this.api.get<Skill[]>(this.BASE_URL);
    }

    createSkill(name: string): Observable<Skill> {
        return this.api.post<Skill>(this.BASE_URL, { name });
    }
}
