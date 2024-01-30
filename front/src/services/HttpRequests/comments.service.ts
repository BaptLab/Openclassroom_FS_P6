import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommentText } from 'src/app/interfaces/comment.interface';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  constructor(private httpClient: HttpClient) {}

  private baseUrl = 'http://localhost:8080';
  private pathService = '/api';

  public getComments(articleId: string | null): Observable<CommentText[]> {
    return this.httpClient.get<CommentText[]>(
      `${this.baseUrl}${this.pathService}/${articleId}/comments`
    );
  }

  public postComment(
    userId: string | null,
    comment: CommentText,
    articleId: string | null
  ): Observable<CommentText> {
    return this.httpClient.post<CommentText>(
      `${this.baseUrl}${this.pathService}/${userId}/comment/${articleId}`,
      comment
    );
  }
}
