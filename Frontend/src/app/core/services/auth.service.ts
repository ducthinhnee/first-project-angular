import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { AuthStorageService } from './auth-storage.service';

export interface LoginResponse {
  jwt: string;
  message: string;
  role: 'CANDIDATE' | 'EMPLOYER';
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly BASE_URL = '/api/v1/auth';

  constructor(private api: ApiService) {}
  private authStorage = inject(AuthStorageService);

  register(payload: {
    email: string;
    password: string;
    role: 'CANDIDATE' | 'EMPLOYER';
  }): Observable<any> {
    return this.api.post(`${this.BASE_URL}/register`, payload);
  }

  login(payload: {
    email: string;
    password: string;
  }): Observable<LoginResponse> {
    return this.api.post<LoginResponse>(
      `${this.BASE_URL}/login`,
      payload
    );
  }

  isLoggedIn(): boolean {
    const token = this.authStorage.getToken();
    if (!token) return false;

    return !this.isTokenExpired(token);
  }

  private isTokenExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const exp = payload.exp * 1000;
      return Date.now() > exp;
    } catch {
      return true;
    }
  }

  logout() {
    this.authStorage.clear();
  }
}
