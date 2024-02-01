// CardComponent interface
import { Theme } from 'src/app/interfaces/theme.interface';

interface Card {
  title: string;
  description: string;
  id?: number;
  showButton?: boolean;
  author?: string;
  buttonText?: string;
  theme?: Theme;
  updatedAt?: Date;
}

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss'],
})
export class CardComponent implements OnInit {
  @Input() card: Card = {
    title: '',
    description: '',
  };

  @Input() buttonText: string | null = 'Click me !';
  @Input() showButton: boolean = false;
  @Input() btnAction: () => void = () => {};

  // Emit an event with the theme id when the button is clicked
  @Output() buttonClick: EventEmitter<number> = new EventEmitter<number>();

  constructor() {}

  ngOnInit(): void {}

  // Modify the click handler to emit the theme id
  handleButtonClick(): void {
    if (this.card.theme && this.card.theme.id) {
      this.buttonClick.emit(this.card.theme.id);
    }
  }
}
