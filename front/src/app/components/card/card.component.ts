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

import { Component, Input, OnInit } from '@angular/core';

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

  constructor() {}

  ngOnInit(): void {}
}
