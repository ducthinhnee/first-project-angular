import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { AuthService } from '../../../../core/services/auth.service';
import { AuthStorageService } from '../../../../core/services/auth-storage.service';
import { MessageModule } from 'primeng/message';

@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  imports: [
    ReactiveFormsModule,
    RouterModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    CheckboxModule,
    MessageModule
  ]
})
export class LoginComponent implements OnInit {
  form!: FormGroup;
  errorMessage = signal<string | null>(null);
  successMessage = signal<string | null>(null);
  loading = signal(false);

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private authStorage: AuthStorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      rememberMe: [false]
    });
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.errorMessage.set(null);
    this.successMessage.set(null);

    this.authService.login(this.form.value).subscribe({
      next: res => {
        this.authStorage.setAuth(res.jwt, {
          email: this.form.value.email,
          role: res.role
        });
        this.loading.set(false);
        this.successMessage.set('Login successfully! Redirecting to Homepage...');

        setTimeout(() => {
          this.router.navigate(['/']);
        }, 1500);

        // redirect theo role
        // if (res.role === 'EMPLOYER') {
        //   this.router.navigate(['/employer/dashboard']);
        // } else {
        //   this.router.navigate(['/']);
        // }
      },
      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err?.error?.message || 'Login failed. Please try again.')
      }
    });
  }
}
