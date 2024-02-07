import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Theme } from 'src/app/interfaces/theme.interface';
import { User } from 'src/app/interfaces/user.interface';
import { ThemeService } from 'src/services/HttpRequests/theme.service';
import { UserService } from 'src/services/HttpRequests/user.service';
import { SessionService } from 'src/services/session/session.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit, OnDestroy {
  @Input() themes: Theme[] = [];

  private userThemesSubscription: Subscription | undefined;

  constructor(
    private userService: UserService,
    private router: Router,
    private themeService: ThemeService,
    private sessionsService: SessionService
  ) {}

  subscribedThemes: number[] = [];
  buttonTextValue: boolean = false;

  userId: string | null = '';

  // Initialize with empty strings
  usernameField: string = '';
  emailField: string = '';

  ngOnInit(): void {
    this.userId = localStorage.getItem('user_id');
    // Set initial values from getUserInfos
    this.getUserInfos(this.userId);

    // Subscribe to user themes
    this.userThemesSubscription = this.themeService
      .getUserThemes(this.userId)
      .subscribe((receivedThemes) => {
        this.themes = receivedThemes;
      });
  }

  updateUser(user: User): void {
    console.log(
      'Data passed in request : ',
      'userId :',
      this.userId,
      'updatedUser : ',
      user
    );
    this.userService
      .updateUser(this.userId, user)
      .subscribe((updatedUser: User) => {
        if (updatedUser != null) {
          console.log('User updated successfully!', updatedUser);
          // this.router.navigate(['/articles']);
        } else {
          console.error('Error updating the user :/');
        }
      });
  }

  getUserInfos(userId: string | null): void {
    this.userService.getUser(userId).subscribe((user: User) => {
      this.usernameField = user.username;
      this.emailField = user.email;
    });
  }

  logOut(): void {
    console.log(this.sessionsService);
    this.sessionsService.logOut();
    if (this.sessionsService.isLogged === false) {
      this.router.navigate(['/login']);
    } else {
      ('There was an error while trying to disconnect');
    }
  }

  getToggleSubscriptionMethod(theme: Theme): () => void {
    return () => this.toggleSubscription(theme);
  }

  toggleSubscription(theme: Theme): void {
    const themeId = theme.id.toString();

    if (!theme.isSubscribed) {
      console.log('Je me désabonne');
      this.themeService
        .unsubscribeToTheme(this.userId, themeId)
        .subscribe(() => {
          this.subscribedThemes = this.subscribedThemes.filter(
            (id) => id !== theme.id
          );
          theme.isSubscribed = true;
        });
    } else {
      console.log('Je mabonne');

      this.themeService.subscribeToTheme(this.userId, themeId).subscribe(() => {
        this.subscribedThemes.push(theme.id);
        theme.isSubscribed = false;
      });
    }
  }

  ngOnDestroy(): void {
    if (this.userThemesSubscription) {
      this.userThemesSubscription.unsubscribe();
    }
  }
}
