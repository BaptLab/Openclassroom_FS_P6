import {
  ChangeDetectorRef,
  Component,
  Input,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Article } from 'src/app/interfaces/article.interface';
import { CommentText } from 'src/app/interfaces/comment.interface';
import { User } from 'src/app/interfaces/user.interface';
import { ArticleService } from 'src/services/HttpRequests/article.service';
import { CommentService } from 'src/services/HttpRequests/comments.service';
import { UserService } from 'src/services/HttpRequests/user.service';

@Component({
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.scss'],
})
export class ArticleDetailsComponent implements OnInit, OnDestroy {
  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private userService: UserService,
    private commentService: CommentService,
    private cdr: ChangeDetectorRef
  ) {}

  comments: CommentText[] = [];
  responsiveCommentText: { description: string } = { description: '' };

  articleId: string | null = '';
  userId: string | null = '';
  articleAuthor: string | null = '';

  article: any = {};
  commentText: { description: string } = { description: '' };

  private articleSubscription: Subscription | undefined;
  private userSubscription: Subscription | undefined;
  private commentsSubscription: Subscription | undefined;

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.userId = localStorage.getItem('user_id');
      const articleIdLong = +params['id'];
      this.articleId = articleIdLong.toString();
      this.getArticleData(this.articleId);
      this.getComments(this.articleId);
    });
  }

  updateFormData(property: string, value: any): void {
    this.commentText.description = value;
  }

  updateResponsiveFormData(event: any): void {
    const value = event.target.value;
    this.responsiveCommentText.description = value;
  }

  getArticleData(articleId: string | null): void {
    this.articleSubscription = this.articleService
      .getArticleById(articleId)
      .subscribe(
        (article: Article) => {
          this.article = article;
          this.loadAuthor();
        },
        (error: Error) => {
          console.error('Error fetching the article details : ', error);
        }
      );
  }

  private loadAuthor(): void {
    this.userSubscription = this.userService
      .getUser(this.article.userId.toString())
      .subscribe(
        (user: User) => {
          this.article.author = user.username;
          this.articleAuthor = user.username;
        },
        (error) => {
          console.error('Error fetching author:', error);
        }
      );
  }

  getComments(articleId: string | null): void {
    this.commentsSubscription = this.commentService
      .getComments(articleId)
      .subscribe(
        (receivedComments: CommentText[]) => {
          this.comments = receivedComments;
        },
        (error) => {
          console.error('Error fetching comments:', error);
        }
      );
  }

  postComment(): void {
    this.commentService
      .postComment(this.userId, this.commentText, this.articleId)
      .subscribe(
        (comment: CommentText) => {
          this.comments.push(comment);
        },
        (error: Error) => {
          console.error('Error posting comment:', error);
        }
      );
  }

  postResponsiveComment(): void {
    this.commentService
      .postComment(this.userId, this.responsiveCommentText, this.articleId)
      .subscribe(
        (comment: CommentText) => {
          this.comments.push(comment);
        },
        (error: Error) => {
          console.error('Error posting comment:', error);
        }
      );
  }

  ngOnDestroy(): void {
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
    if (this.commentsSubscription) {
      this.commentsSubscription.unsubscribe();
    }
  }
}
