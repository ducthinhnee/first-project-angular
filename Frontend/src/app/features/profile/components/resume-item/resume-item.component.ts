import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { Resume } from '../../models/resume.model';
import { ResumeFileNamePipe } from '../../../../shared/pipes/resume-file.pipe';

@Component({
  selector: 'app-resume-item',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, ResumeFileNamePipe],
  templateUrl: './resume-item.component.html',
})
export class ResumeItemComponent {
  @Input({ required: true }) resume!: Resume;
  @Output() delete = new EventEmitter<number>();
}
