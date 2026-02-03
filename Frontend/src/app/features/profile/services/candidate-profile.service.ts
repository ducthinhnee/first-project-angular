import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { Skill } from '../models/skill.model';
import { CandidateProfile } from '../models/candidate-profile.model';

@Injectable({ providedIn: 'root' })
export class CandidateProfileService {
    private readonly BASE_URL = '/api/v1/candidate-profiles';

    constructor(private api: ApiService) {}

    getProfile() {
        return this.api.get<CandidateProfile>(this.BASE_URL);
    }

    updateProfile(payload: {
        fullName: string;
        phone: string;
        summary: string;
        skills: { id: number }[];
    }) {
        return this.api.put<CandidateProfile>(this.BASE_URL, payload);
    }
}
