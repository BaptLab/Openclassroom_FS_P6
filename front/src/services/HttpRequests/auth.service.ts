import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {
  AuthSuccess,
  LoginRequest,
  RegisterRequest,
  ReturnedMessage,
} from 'src/app/interfaces/auth.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private httpClient: HttpClient) {}

  private baseUrl = 'http://localhost:8080';
  private pathService = '/api/auth';

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(
      `${this.baseUrl}${this.pathService}/login`,
      loginRequest
    );
  }

  public register(
    registerRequest: RegisterRequest
  ): Observable<ReturnedMessage> {
    return this.httpClient.post<ReturnedMessage>(
      `${this.baseUrl}${this.pathService}/register`,
      registerRequest
    );
  }
}
