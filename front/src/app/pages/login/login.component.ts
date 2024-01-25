import { Component } from '@angular/core';
import { AuthService } from 'src/app/HttpRequests/auth.service';
import { AuthSuccess, LoginRequest } from 'src/app/interfaces/auth.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  constructor(private authService: AuthService) {}

  login(formData: LoginRequest): void {
    const loginRequest: LoginRequest = {
      email: formData.email,
      password: formData.password,
    };

    this.authService.login(loginRequest).subscribe(
      (authSuccess: AuthSuccess) => {
        // Handle successful login
        console.log('Login successful', authSuccess);
      },
      (error) => {
        // Handle login error
        console.error('Login error', error);
      }
    );
  }
}
