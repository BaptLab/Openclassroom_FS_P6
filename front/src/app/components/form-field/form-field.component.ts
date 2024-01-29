import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';

@Component({
  selector: 'app-form-field',
  templateUrl: './form-field.component.html',
  styleUrls: ['./form-field.component.scss'],
})
export class FormFieldComponent implements OnInit {
  @Input() label: string = '';
  @Input() formDataProperty: string = '';
  @Input() placeholder: string = '';
  @Input() inputType: string | undefined = '';
  @Input() selectOptions: { value: any; label: string }[] | undefined = [];
  @Output() inputChange = new EventEmitter<string>();

  selectedValue: any;
  inputValue: string = '';

  ngOnInit(): void {}

  onInputChange(event: Event): void {
    if (this.inputType === 'select') {
      const selectElement = event.target as HTMLSelectElement;
      const value = selectElement.value;
      this.selectedValue = value;
      this.inputChange.emit(value);
    } else {
      const inputElement = event.target as HTMLInputElement;
      const value = inputElement.value;
      this.inputValue = value;
      this.inputChange.emit(value);
    }
  }
}
