import { Component } from '@angular/core';
import { AuthService } from 'src/services/HttpRequests/auth.service';
import {
  RegisterRequest,
  ReturnedMessage,
} from 'src/app/interfaces/auth.interface';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  constructor(private authService: AuthService) {}

  register(formData: any): void {
    const registerRequest: RegisterRequest = {
      email: formData.email,
      username: formData.username,
      password: formData.password,
    };

    this.authService.register(registerRequest).subscribe(
      (registerSuccess: ReturnedMessage) => {
        console.log('Register successful!', registerSuccess);
        // You can access the 'message' property here if needed
        console.log('Message:', registerSuccess.message);
      },
      (error) => {
        console.error('Register error', error);
      }
    );
  }
}
