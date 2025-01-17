import {
  Component,
  Input,
  Output,
  EventEmitter,
  OnInit,
  OnDestroy,
} from '@angular/core';

@Component({
  selector: 'app-form-field',
  templateUrl: './form-field.component.html',
  styleUrls: ['./form-field.component.scss'],
})
export class FormFieldComponent implements OnInit, OnDestroy {
  @Input() label: string = '';
  @Input() formDataProperty: string = '';
  @Input() placeholder: string = '';
  @Input() value: string | undefined = '';
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

  ngOnDestroy(): void {}
}
