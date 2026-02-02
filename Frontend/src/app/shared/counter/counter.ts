import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-counter',
  template: `
    <button (click)="decrease()">-</button>
    <span> {{ count }} </span>
    <button (click)="increase()">+</button>
  `,
})
export class CounterComponent {
  @Input() count = 0;
  @Output() countChange = new EventEmitter<number>();

  increase() {
    this.countChange.emit(this.count + 1);
  }

  decrease() {
    this.countChange.emit(this.count - 1);
  }
}
