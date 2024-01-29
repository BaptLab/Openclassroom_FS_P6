import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Article } from 'src/app/interfaces/article.interface';
import { ArticleService } from 'src/services/HttpRequests/article.service';

@Component({
  selector: 'app-article-creation',
  templateUrl: './article-creation.component.html',
  styleUrls: ['./article-creation.component.scss'],
})
export class ArticleCreationComponent implements OnInit {
  constructor(private articleService: ArticleService, private router: Router) {}

  userId: string | null = null;

  ngOnInit(): void {
    const userIdString = localStorage.getItem('user_id');
  }

  postArticle(formData: Article): void {
    this.articleService
      .postArticle(this.userId, formData)
      .subscribe((article: Article) => {
        if (article != null) {
          console.log('Article successfully created!');
          this.router.navigate(['/articles']);
        } else {
          console.error('Error creating the article :/');
        }
      });
  }
}
