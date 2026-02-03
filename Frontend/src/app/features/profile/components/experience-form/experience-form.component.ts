import { Component, inject, OnInit, PLATFORM_ID, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { DatePickerModule } from 'primeng/datepicker';
import { DatePipe, isPlatformBrowser } from '@angular/common';
import { ExperiencePayload } from '../../models/experience.model';
import { ExperienceService } from '../../services/experience.service';

@Component({
  selector: 'app-experience-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    InputTextModule,
    DatePickerModule,
    ButtonModule,
    MessageModule,
    DatePipe,
  ],
  templateUrl: './experience-form.component.html',
})
export class ExperienceFormComponent implements OnInit {
    private platformId = inject(PLATFORM_ID);
    isBrowser = isPlatformBrowser(this.platformId);
    constructor(private experienceService: ExperienceService) {}

    experiences = signal<ExperiencePayload[]>([]);
    loading = signal(false);
    successMessage = '';
    errorMessage = '';

    ngOnInit(): void {
        this.loadExperiences();
    }

    // load experience form
    loadExperiences() {
        this.loading.set(true);

        this.experienceService.getMyExperiences().subscribe({
            next: (res) => {
                this.experiences.set(res);
                this.loading.set(false);
            },
            error: () => {
                this.loading.set(false);
                this.errorMessage = 'Failed to load experiences';
            }
        });
    }

    // add experience form
    form = new FormGroup({
        companyName: new FormControl('', {
            nonNullable: true,
            validators: [Validators.required],
        }),
        position: new FormControl('', {
            nonNullable: true,
            validators: [Validators.required],
        }),
        startDate: new FormControl<Date | null>(null, Validators.required),
        endDate: new FormControl<Date | null>(null),
    });

    submit() {
        if (this.form.invalid) {
        this.form.markAllAsTouched();
        return;
        }

        this.loading.set(true);
        this.successMessage = '';
        this.errorMessage = '';

        const value = this.form.value;

        const payload = {
            companyName: value.companyName!,
            position: value.position!,
            startDate: value.startDate!.toISOString(),
            endDate: value.endDate ? value.endDate.toISOString() : null,
        };

        this.experienceService.createExperience(payload).subscribe({
            next: (createdExp) => {
                this.loading.set(false);
                this.successMessage = 'Experience added successfully!';

                this.experiences.update((list) => [...list, createdExp]);
                this.form.reset();
            },
            error: (err) => {
                this.loading.set(false);
                this.errorMessage = err?.error?.message || 'Failed to add experience';
            },
        });
    }

    hasError(control: string, error: string) {
        const c = this.form.get(control);
        return c?.touched && c.hasError(error);
    }

    // remove experience from the list
    removeExperience(id: number, index: number) {
        if (!confirm('Are you sure you want to delete this experience?')) {
            return;
        }

        this.experienceService.deleteExperience(id).subscribe({
            next: () => {
                this.experiences.update((list) => list.filter((_, i) => i !== index));
                this.successMessage = 'Experience removed successfully!';
            },
            error: () => {
                this.errorMessage = 'Failed to delete experience';
            }
        });
    }
}
