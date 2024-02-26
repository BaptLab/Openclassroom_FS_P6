import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ThemeService } from 'src/services/HttpRequests/theme.service';
import { Theme } from 'src/app/interfaces/theme.interface';
import { SessionService } from 'src/services/session/session.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit, OnDestroy {
  @Input() themes: Theme[] = [];

  userId: string | null = '';
  subscribedThemes: number[] = [];
  buttonTextValue: boolean = false;

  private themesSubscription: Subscription | undefined;
  private userThemesSubscription: Subscription | undefined;

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
    this.themesSubscription = this.themeService
      .getThemes()
      .subscribe((receivedThemes) => {
        this.themes = receivedThemes;
        this.userThemesSubscription = this.themeService
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

  ngOnDestroy(): void {
    if (this.themesSubscription) {
      this.themesSubscription.unsubscribe();
    }
    if (this.userThemesSubscription) {
      this.userThemesSubscription.unsubscribe();
    }
  }
}
