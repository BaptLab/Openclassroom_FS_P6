import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
export class ProfileComponent implements OnInit {
  @Input() themes: Theme[] = [];

  constructor(
    private userService: UserService,
    private router: Router,
    private themeService: ThemeService,
    private sessionsService: SessionService
  ) {}

  userId: string | null = '';

  usernameField: string = '';
  emailField: string = '';

  ngOnInit(): void {
    this.userId = localStorage.getItem('user_id');
    this.getUserInfos(this.userId);
    this.themeService.getThemes().subscribe((receivedThemes) => {
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
    this.userService.updateUser(this.userId, user).subscribe((user: User) => {
      if (user != null) {
        console.log('user updated successfully !', user);
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
}
