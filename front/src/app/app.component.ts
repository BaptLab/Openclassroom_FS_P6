import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/services/session/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  constructor(private sessionsService: SessionService) {}
  title = 'front';

  token: string | null = '';

  ngOnInit(): void {
    this.token = localStorage.getItem('user_token');
    if (this.token) {
      if (this.checkForTokenExpiration(this.token)) {
        this.sessionsService.isLogged = true;
        console.log('You are still connected!');
      } else {
        this.sessionsService.isLogged = false;
        console.log('Session expired, connect again!');
        localStorage.clear();
      }
    } else {
      console.log('No token found, please log in.');
    }
  }

  checkForTokenExpiration(token: string | null): boolean {
    if (token) {
      const expiry = JSON.parse(atob(token.split('.')[1])).exp;
      const isTokenValid = Math.floor(new Date().getTime() / 1000) < expiry;
      return isTokenValid;
    }
    return false;
  }

  ngOnDestroy(): void {}
}
