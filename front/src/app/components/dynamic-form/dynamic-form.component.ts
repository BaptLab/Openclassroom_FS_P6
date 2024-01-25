import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss'],
})
export class DynamicFormComponent implements OnInit {
  @Input() formTitle: string = 'Title';
  @Input() formFields: { label: string }[] = [];
  @Input() submitBtnText: string = 'Btn text';
  @Input() onSubmit: () => void = () => {};

  constructor() {}

  ngOnInit(): void {}
}
