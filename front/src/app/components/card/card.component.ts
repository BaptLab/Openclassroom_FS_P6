import { Component, Input, OnInit } from '@angular/core';
import { Theme } from 'src/app/interfaces/theme.interface';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss'],
})
export class CardComponent implements OnInit {
  @Input() theme: Theme = { title: '', description: '', id: 0 };

  constructor() {}

  ngOnInit(): void {}
}
