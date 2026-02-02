import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ButtonModule } from 'primeng/button';
import { FileUploadModule } from 'primeng/fileupload';
import { ResumeItemComponent } from '../resume-item/resume-item.component';


@Component({
  selector: 'app-resume-list',
  standalone: true,
  imports: [
    CommonModule,
    ButtonModule,
    FileUploadModule,
    ResumeItemComponent,
  ],
  templateUrl: './resume-list.component.html',
})
export class ResumeListComponent {
  @Output() upload = new EventEmitter<File>();
  @Output() delete = new EventEmitter<number>();

  resumes = [
    { id: 1, name: 'Professional Resume', size: '3.5 MB' },
    { id: 2, name: 'Product Designer', size: '4.7 MB' },
    { id: 3, name: 'Visual Designer', size: '1.3 MB' },
  ];

  onUpload(event: any) {
    this.upload.emit(event.files[0]);
  }
}
