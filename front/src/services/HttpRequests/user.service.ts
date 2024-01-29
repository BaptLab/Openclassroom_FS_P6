import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/app/interfaces/user.interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private httpClient: HttpClient) {}

  private baseUrl = 'http://localhost:8080';
  private pathService = '/api/user';

  public getUser(userId: string | null): Observable<User> {
    return this.httpClient.get<User>(
      `${this.baseUrl}${this.pathService}/${userId}`
    );
  }

  public updateUser(
    userId: string | null,
    updatedUser: User
  ): Observable<User> {
    return this.httpClient.put<User>(
      `${this.baseUrl}${this.pathService}/${userId}`,
      updatedUser
    );
  }
}
