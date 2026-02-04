import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'salary' })
export class SalaryPipe implements PipeTransform {
  transform(min: number, max: number): string {
    if (min == null || max == null) return '';
    return `$${min.toLocaleString()} - $${max.toLocaleString()}`;
  }
}
