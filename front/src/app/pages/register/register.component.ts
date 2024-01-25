import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/HttpRequests/auth.service';
import { RegisterRequest } from 'src/app/interfaces/auth.interface';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  constructor(private authService: AuthService) {}
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  register(formData: any): void {
    const registerRequest: RegisterRequest = {
      email: formData['Adresse e-mail'],
      username: formData["Nom d'utilisateur"],
      password: formData['Mot de passe'],
    };
  }
}
