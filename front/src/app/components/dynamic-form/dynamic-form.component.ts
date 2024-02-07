import {
  Component,
  Input,
  Output,
  EventEmitter,
  OnInit,
  OnDestroy,
} from '@angular/core';
import { Subscription } from 'rxjs';
import { ThemeService } from 'src/services/HttpRequests/theme.service';

interface FormField {
  label: string;
  formDataProperty: string;
  placeholder: string;
  inputType?: string | undefined;
  selectOptions?: { value: any; label: string }[] | undefined;
  value?: string | undefined;
}

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss'],
})
export class DynamicFormComponent implements OnInit, OnDestroy {
  @Input() formTitle: string = 'Title';
  @Input() submitBtnText: string = '';
  @Output() formSubmit = new EventEmitter<any>();

  @Input() formFields: FormField[] = [];

  private themeSubscription: Subscription | undefined;

  formData: { [key: string]: any } = {};
  invalidInputMsg: string | null = '';
  isFormSubmitted: boolean = false; // New flag to track form submission

  constructor(private themeService: ThemeService) {}

  ngOnInit(): void {
    console.log(this.formFields);
    // Check if there's a form field with inputType 'select'
    const selectField = this.formFields.find(
      (field) => field.inputType === 'select'
    );

    // If there is, fetch themes from the API and populate selectOptions
    if (selectField) {
      this.themeSubscription = this.themeService
        .getThemes()
        .subscribe((themes) => {
          selectField.selectOptions = themes.map((theme) => ({
            value: theme.title,
            label: theme.title,
          }));
        });
    }

    // Initialize form data with default values or values passed from formFields
    this.formFields.forEach((field) => {
      this.formData[field.formDataProperty] = field.value || '';
    });
  }

  updateFormData(property: string, value: any): void {
    this.isFormSubmitted = false;
    this.formData[property] = value;
  }

  customValidationRules: {
    [key: string]: { rule: (value: any) => boolean; errorMessage: string };
  } = {
    // Add custom validation rules here based on formData property
    email: {
      rule: (value) => /\S+@\S+\.\S+/.test(value),
      errorMessage: 'Your email should have a format like xxxx@xxx.com',
    },
    password: {
      rule: (value) =>
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(
          value
        ),
      errorMessage:
        'Your password must contain at least 1 uppercase letter, 1 lowercase letter, and 1 special character (@, !, #, %...)',
    },
    username: {
      rule: (value) => value.length >= 8,
      errorMessage: 'Your username should at least contain 8 characters',
    },
  };

  submitForm(event: Event): void {
    event.preventDefault();
    this.isFormSubmitted = true; // Set the flag when the form is submitted
    const isValid = this.validateForm();
    if (isValid) {
      this.formSubmit.emit(this.formData);
      console.log(this.formData);
    } else {
      console.log('Form validation failed.');
    }
  }

  validateForm(): boolean {
    // Reset error message
    this.invalidInputMsg = '';

    // Check custom validation rules for each form field
    for (const field of this.formFields) {
      const validationRule =
        this.customValidationRules[field.formDataProperty]?.rule;

      // Check if the field is empty
      if (!this.formData[field.formDataProperty]) {
        this.invalidInputMsg = 'This field cannot be empty.';
        return false;
      } else if (
        validationRule &&
        !validationRule(this.formData[field.formDataProperty])
      ) {
        // Set custom error message for the specific validation rule
        this.invalidInputMsg =
          this.customValidationRules[field.formDataProperty].errorMessage;
        return false;
      }
    }

    return true; // All validations passed
  }

  ngOnDestroy(): void {
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
  }
}
