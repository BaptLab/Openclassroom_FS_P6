import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/app/interfaces/user.interface';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ReturnedMessage } from 'src/app/interfaces/auth.interface';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  constructor(private httpClient: HttpClient) {}

  private baseUrl = 'http://localhost:8080';
  private pathService = '/api/themes';

  public getThemes(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(`${this.baseUrl}${this.pathService}`);
  }

  public getUserThemes(userId: string | null): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(
      `${this.baseUrl}${this.pathService}/${userId}`
    );
  }

  public subscribeToTheme(
    userId: string | null,
    themeId: string | null
  ): Observable<ReturnedMessage> {
    return this.httpClient.post<ReturnedMessage>(
      `${this.baseUrl}${this.pathService}/${userId}/subscribe/${themeId}`,
      {}
    );
  }

  public unsubscribeToTheme(
    userId: string | null,
    themeId: string | null
  ): Observable<ReturnedMessage> {
    return this.httpClient.delete<ReturnedMessage>(
      `${this.baseUrl}${this.pathService}/${userId}/subscribe/${themeId}`,
      {}
    );
  }
}
