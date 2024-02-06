import { Component, Input, OnInit } from '@angular/core';
import { ThemeService } from 'src/services/HttpRequests/theme.service';
import { Theme } from 'src/app/interfaces/theme.interface';
import { SessionService } from 'src/services/session/session.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit {
  @Input() themes: Theme[] = [];

  userId: string | null = '';
  subscribedThemes: number[] = [];
  buttonTextValue: boolean = false;

  constructor(
    private themeService: ThemeService,
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.userId = localStorage.getItem('user_id');
    this.loadThemes();
    console.log(this.sessionService.isLogged);
  }

  private loadThemes(): void {
    this.themeService.getThemes().subscribe((receivedThemes) => {
      this.themes = receivedThemes;
      this.themeService
        .getUserThemes(this.userId)
        .subscribe((subscribedThemes) => {
          this.subscribedThemes = subscribedThemes.map((theme) => theme.id);
          this.updateButtonStates();
        });
    });
  }

  private updateButtonStates(): void {
    this.themes.forEach((theme) => {
      theme.isSubscribed = this.subscribedThemes.includes(theme.id);
    });
  }

  getToggleSubscriptionMethod(theme: Theme): () => void {
    return () => this.toggleSubscription(theme);
  }

  toggleSubscription(theme: Theme): void {
    const themeId = theme.id.toString();

    if (theme.isSubscribed) {
      this.themeService
        .unsubscribeToTheme(this.userId, themeId)
        .subscribe(() => {
          this.subscribedThemes = this.subscribedThemes.filter(
            (id) => id !== theme.id
          );
          theme.isSubscribed = false;
        });
    } else {
      this.themeService.subscribeToTheme(this.userId, themeId).subscribe(() => {
        this.subscribedThemes.push(theme.id);
        theme.isSubscribed = true;
      });
    }
  }
}
