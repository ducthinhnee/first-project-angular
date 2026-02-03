import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { Skill } from '../models/skill.model';
import { Resume } from '../models/resume.model';

@Injectable({ providedIn: 'root' })
export class ResumeService {
    private readonly BASE_URL = '/api/v1/resumes';

    constructor(private api: ApiService) {}

    getAll() {
        return this.api.get<Resume[]>(this.BASE_URL);
    }

    upload(file: File) {
        const formData = new FormData();
        formData.append('file', file);

        return this.api.post<Resume>(`${this.BASE_URL}/upload`, formData);
    }

    delete(id: number) {
        return this.api.delete<void>(`${this.BASE_URL}/${id}`);
    }
}
