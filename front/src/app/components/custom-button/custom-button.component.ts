import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-custom-button',
  templateUrl: './custom-button.component.html',
  styleUrls: ['./custom-button.component.scss'],
})
export class CustomButtonComponent {
  @Input() buttonText: string = 'Click Me';
  @Input() buttonColor: string = 'primary';
  @Input() isDisabled: boolean = false;
  @Input() clickHandler: () => void = () => {};

  constructor(private router: Router) {}

  handleButtonClick(): void {
    this.clickHandler();
  }
}
