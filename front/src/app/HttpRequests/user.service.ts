import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private httpClient: HttpClient) {}

  private baseUrl = 'http://localhost:8080';
  private pathService = '/api/user';

  public getUser(userId: number): Observable<User> {
    return this.httpClient.get<User>(
      `${this.baseUrl}${this.pathService}/${userId}`
    );
  }

  public updateUser(userId: number, updatedUser: User): Observable<User> {
    return this.httpClient.post<User>(
      `${this.baseUrl}${this.pathService}/${userId}`,
      updatedUser
    );
  }
}
