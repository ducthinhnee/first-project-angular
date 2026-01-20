import { Component } from '@angular/core';
import { CounterComponent } from '../shared/counter/counter';

@Component({
  selector: 'app-home',
  templateUrl: './home-component.html',
  styleUrl: './home-component.css',
  imports: [CounterComponent],
})
export class HomeComponent {
  counter = 5;

  onCounterChange(value: number) {
    this.counter = value;
  }
}
