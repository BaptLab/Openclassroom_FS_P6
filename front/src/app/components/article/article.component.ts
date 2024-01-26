import { Component, Input, OnInit } from '@angular/core';
import { Article } from 'src/app/interfaces/article.interface';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/services/HttpRequests/user.service';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss'],
})
export class ArticleComponent implements OnInit {
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

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadAuthor();
  }

  private loadAuthor(): void {
    this.userService.getUser(this.article.userId).subscribe(
      (user: User) => {
        this.author = user.username;
      },
      (error) => {
        console.error('Error fetching author:', error);
      }
    );
  }
}
