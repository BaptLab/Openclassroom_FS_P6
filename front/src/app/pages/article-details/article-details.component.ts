import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
export class ArticleDetailsComponent implements OnInit {
  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private userService: UserService,
    private commentService: CommentService
  ) {}

  @Input() comments: CommentText[] = [];

  articleId: string | null = '';
  userId: string | null = '';

  article: any = {};
  commentText: CommentText = { description: '' };

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.userId = localStorage.getItem('user_id');
      const articleIdLong = +params['id'];
      this.articleId = articleIdLong.toString();
      this.getArticleData(this.articleId);
      this.getComments(this.articleId);
    });
  }

  getArticleData(articleId: string | null): void {
    this.articleService.getArticleById(articleId).subscribe(
      (article: Article) => {
        console.log(article);
        this.article = article;
        this.loadAuthor();
      },
      (error: Error) => {
        console.error('Error fetching the article details : ', error);
      }
    );
  }

  private loadAuthor(): void {
    this.userService.getUser(this.article.userId.toString()).subscribe(
      (user: User) => {
        this.article.author = user.username;
      },
      (error) => {
        console.error('Error fetching author:', error);
      }
    );
  }

  getComments(articleId: string | null): void {
    this.commentService.getComments(articleId).subscribe(
      (receivedComments: CommentText[]) => {
        this.comments = receivedComments;
        console.log('Commentaires reçus de la DB :', receivedComments);
      },
      (error) => {
        console.error('Error fetching comments:', error);
      }
    );
  }

  postComment(comment: CommentText): void {
    this.commentService
      .postComment(this.userId, comment, this.articleId)
      .subscribe((comment: CommentText) => {
        return comment;
      });
    (error: Error) => {
      return error;
    };
  }
}
