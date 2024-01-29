import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article as Article } from 'src/app/interfaces/article.interface';
import { ReturnedMessage } from 'src/app/interfaces/auth.interface';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  constructor(private httpClient: HttpClient) {}

  private baseUrl = 'http://localhost:8080';
  private pathService = '/api';

  public getArticles(): Observable<Article[]> {
    console.log('getting the articles !');
    return this.httpClient.get<Article[]>(
      `${this.baseUrl}${this.pathService}/articles`
    );
  }

  public postArticle(
    userId: string | null,
    article: Article
  ): Observable<Article> {
    return this.httpClient.post<Article>(
      `${this.baseUrl}${this.pathService}/${userId}/article`,
      article
    );
  }

  public subscribeToTheme(
    userId: string | null,
    articleId: number
  ): Observable<ReturnedMessage> {
    return this.httpClient.post<ReturnedMessage>(
      `${this.baseUrl}${this.pathService}/api/article/${userId}/subscribe/${articleId}`,
      {}
    );
  }

  public unsubscribeToTheme(
    userId: string | null,
    articleId: number
  ): Observable<ReturnedMessage> {
    return this.httpClient.delete<ReturnedMessage>(
      `${this.baseUrl}${this.pathService}/api/article/${userId}/subscribe/${articleId}`,
      {}
    );
  }
}
