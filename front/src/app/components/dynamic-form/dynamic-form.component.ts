import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { ThemeService } from 'src/services/HttpRequests/theme.service';

interface FormField {
  label: string;
  formDataProperty: string;
  placeholder: string;
  inputType?: string | undefined;
  selectOptions?: { value: any; label: string }[] | undefined;
}

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss'],
})
export class DynamicFormComponent implements OnInit {
  @Input() formTitle: string = 'Title';
  @Input() submitBtnText: string = 'Btn text';
  @Output() formSubmit = new EventEmitter<any>();

  @Input() formFields: FormField[] = [];

  formData: any = {};

  constructor(private themeService: ThemeService) {}

  ngOnInit(): void {
    // Check if there's a form field with inputType 'select'
    const selectField = this.formFields.find(
      (field) => field.inputType === 'select'
    );

    // If there is, fetch themes from the API and populate selectOptions
    if (selectField) {
      this.themeService.getThemes().subscribe((themes) => {
        selectField.selectOptions = themes.map((theme) => ({
          value: theme.title,
          label: theme.title,
        }));
      });
    }
  }

  updateFormData(property: string, value: any): void {
    this.formData[property] = value;
  }

  submitForm(event: Event): void {
    event.preventDefault();
    this.formSubmit.emit(this.formData);
    console.log(this.formData);
  }
}
