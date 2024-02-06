import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/HttpRequests/auth.service';
import { UserService } from 'src/services/HttpRequests/user.service';
import { AuthSuccess, LoginRequest } from 'src/app/interfaces/auth.interface';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/services/session/session.service';
import { tap, switchMap } from 'rxjs/operators';

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
      emailOrUsername: formData.emailOrUsername,
      password: formData.password,
    };

    this.authService
      .login(loginRequest)
      .pipe(
        tap((authSuccess: AuthSuccess) => {
          console.log('Login successful !', authSuccess);
          localStorage.setItem('user_id', authSuccess.id.toString());
          localStorage.setItem('user_token', authSuccess.token);
        }),
        switchMap((authSuccess: AuthSuccess) =>
          this.userService.getUser(authSuccess.id.toString())
        ),
        tap((user: User) => {
          this.sessionsService.logIn(user);
          this.router.navigate(['/themes']);
        })
      )
      .subscribe(
        () => {},
        (error) => {
          // Handle login error
          console.error('Login error', error);
        }
      );
  }
}
