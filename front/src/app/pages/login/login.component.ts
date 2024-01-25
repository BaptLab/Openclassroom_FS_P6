import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/HttpRequests/auth.service';
import { UserService } from 'src/app/HttpRequests/user.service';
import { AuthSuccess, LoginRequest } from 'src/app/interfaces/auth.interface';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/services/session/session.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  constructor(
    private authService: AuthService,
    private sessionsService: SessionService,
    private userService: UserService,
    private router: Router
  ) {}

  login(formData: LoginRequest): void {
    const loginRequest: LoginRequest = {
      email: formData.email,
      password: formData.password,
    };

    this.authService.login(loginRequest).subscribe(
      (authSuccess: AuthSuccess) => {
        console.log('Login successful !', authSuccess);
        this.userService.getUser(authSuccess.id).subscribe((user: User) => {
          console.log(this.sessionsService.isLogged);
          this.sessionsService.logIn(user);
          console.log(this.sessionsService.isLogged);
        });
        localStorage.setItem('user_id', authSuccess.id.toString());
        localStorage.setItem('user_token', authSuccess.token);
      },
      (error) => {
        // Handle login error
        console.error('Login error', error);
      }
    );
  }
}
