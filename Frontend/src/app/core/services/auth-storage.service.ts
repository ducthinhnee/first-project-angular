import { isPlatformBrowser } from '@angular/common';
import { inject, Injectable, PLATFORM_ID, signal } from '@angular/core';

export interface AuthUser {
  email: string;
  role: 'CANDIDATE' | 'EMPLOYER';
}

@Injectable({ providedIn: 'root' })
export class AuthStorageService {
  private readonly TOKEN_KEY = 'token';
  private readonly USER_KEY = 'user';
  private platformId = inject(PLATFORM_ID);
  private isBrowser = isPlatformBrowser(this.platformId);

  user = signal<AuthUser | null>(null);

  constructor() {
    if (this.isBrowser) {
      const user = this.getUserFromStorage();
      this.user.set(user);
    }
  }

  setAuth(token: string, user: AuthUser) {
    if (!this.isBrowser) return;
    localStorage.setItem(this.TOKEN_KEY, token);
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
    this.user.set(user);
  }

  clear() {
    if (!this.isBrowser) return;
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.user.set(null);
  }

  private getUserFromStorage(): AuthUser | null {
    if (!this.isBrowser) return null;
    const raw = localStorage.getItem(this.USER_KEY);
    return raw ? JSON.parse(raw) : null;
  }

  getToken(): string | null {
    if (!this.isBrowser) return null;
    return localStorage.getItem(this.TOKEN_KEY);
  }
}
