import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileUploadModule } from 'primeng/fileupload';
import { AvatarModule } from 'primeng/avatar';

@Component({
  selector: 'app-avatar-upload',
  standalone: true,
  imports: [CommonModule, FileUploadModule, AvatarModule],
  templateUrl: './avatar-upload.component.html',
})
export class AvatarUploadComponent {
  image?: string;

  onSelect(event: any) {
    const file = event.files[0];
    const reader = new FileReader();
    reader.onload = () => (this.image = reader.result as string);
    reader.readAsDataURL(file);
  }
}
