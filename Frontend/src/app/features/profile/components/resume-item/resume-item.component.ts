import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-resume-item',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule],
  templateUrl: './resume-item.component.html',
})
export class ResumeItemComponent {
  @Input() resume!: { id: number; name: string; size: string };
  @Output() delete = new EventEmitter<number>();
}
