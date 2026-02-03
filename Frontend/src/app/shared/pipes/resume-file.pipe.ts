import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'resumeFileName',
  standalone: true
})
export class ResumeFileNamePipe implements PipeTransform {
  transform(fileUrl: string): string {
    if (!fileUrl) return '';
    return fileUrl.substring(fileUrl.lastIndexOf('_') + 1);
  }
}