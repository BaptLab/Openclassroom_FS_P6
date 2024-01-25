import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-form-field',
  templateUrl: './form-field.component.html',
  styleUrls: ['./form-field.component.scss'],
})
export class FormFieldComponent {
  @Input() label: string = '';
  @Input() formDataProperty: string = '';
  @Output() inputChange = new EventEmitter<string>();

  onInputChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    const value = inputElement.value;
    this.inputChange.emit(value);
  }
}
