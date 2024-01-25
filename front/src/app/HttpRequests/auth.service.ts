import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {
  AuthSuccess,
  LoginRequest,
  RegisterRequest,
  RegisterSuccess,
} from '../interfaces/auth.interface';
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
  ): Observable<RegisterSuccess> {
    return this.httpClient.post<RegisterSuccess>(
      `${this.baseUrl}${this.pathService}/register`,
      registerRequest
    );
  }
}
