import { Component, Input, OnInit } from '@angular/core';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ThemeService } from 'src/services/HttpRequests/theme.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit {
  @Input() themes: Theme[] = [];

  constructor(private themeService: ThemeService) {}

  ngOnInit(): void {
    this.themeService.getThemes().subscribe((receivedThemes) => {
      this.themes = receivedThemes;
    });
  }
}
