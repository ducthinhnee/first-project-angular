import { Component, EventEmitter, inject, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SkillService } from '../../services/skill.service';

@Component({
  selector: 'app-create-skill-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DialogModule,
    ButtonModule,
    InputTextModule,
  ],
  templateUrl: './create-skill-dialog.component.html',
})
export class CreateSkillDialogComponent {
    @Output() created = new EventEmitter<void>();

    private fb = inject(FormBuilder);
    private skillService = inject(SkillService);

    visible = signal(false);
    loading = signal(false);
    errorMessage = signal<string | null>(null);


    form = this.fb.group({
        name: ['', Validators.required],
    });

    open() {
        this.form.reset();
        this.visible.set(true);
    }

    close() {
        this.visible.set(false);
    }

    submit() {
        if (this.form.invalid) return;

        this.loading.set(true);
        this.errorMessage.set(null);

        this.skillService.createSkill(this.form.value.name!).subscribe({
            next: () => {
                this.loading.set(false);
                this.visible.set(false);
                this.created.emit();
            },
            error: (err) => {
                this.loading.set(false);
                const message =
                err.error?.message ||
                err.error?.error ||
                'Create skill failed';

                this.errorMessage.set(message);

                this.form.get('name')?.setErrors({ backend: true });
            },
        });
    }
}
