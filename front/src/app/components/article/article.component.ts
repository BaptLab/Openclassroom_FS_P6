import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Article } from 'src/app/interfaces/article.interface';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/services/HttpRequests/user.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss'],
})
export class ArticleComponent implements OnInit, OnDestroy {
  @Input() article: Article = {
    id: 0,
    title: '',
    description: '',
    theme: '',
    userId: 0,
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  author: string = '';
  private userSubscription: Subscription | undefined;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.loadAuthor();
    console.log(this.article);
  }

  private loadAuthor(): void {
    this.userSubscription = this.userService
      .getUser(this.article.userId.toString())
      .subscribe(
        (user: User) => {
          this.author = user.username;
        },
        (error) => {
          console.error('Error fetching author:', error);
          this.author = 'Unknown Author'; // Set a default or placeholder author name
        }
      );
  }

  public navigateToArticleDetails(): void {
    this.router.navigate(['/article', this.article.id]);
  }

  ngOnDestroy(): void {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }
}
