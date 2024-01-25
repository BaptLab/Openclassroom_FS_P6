import { Component, Input, Output, EventEmitter } from '@angular/core';
import { LoginRequest } from 'src/app/interfaces/auth.interface';

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss'],
})
export class DynamicFormComponent {
  @Input() formTitle: string = 'Title';
  @Input() formFields: { label: string; formDataProperty: string }[] = [];
  @Input() submitBtnText: string = 'Btn text';
  @Output() formSubmit = new EventEmitter<LoginRequest>();

  formData: LoginRequest = {
    email: '',
    password: '',
  };

  updateFormData(property: string, value: string): void {
    (this.formData as any)[property] = value;
  }

  submitForm(event: Event): void {
    event.preventDefault();
    this.formSubmit.emit(this.formData);
    console.log(this.formData);
  }
}
