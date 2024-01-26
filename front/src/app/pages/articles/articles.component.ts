import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Article } from 'src/app/interfaces/article.interface';
import { ArticleService } from 'src/services/HttpRequests/article.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss'],
})
export class ArticlesComponent implements OnInit {
  @Input() articles: Article[] = [];

  constructor(private articleService: ArticleService, private router: Router) {}

  ngOnInit(): void {
    this.articleService.getArticles().subscribe(
      (receivedArticles: Article[]) => {
        this.articles = receivedArticles;
      },
      (error) => {
        console.error('Error fetching articles:', error);
        // Handle error if needed
      }
    );
  }

  navigateToArticleCreation(): void {
    this.router.navigate(['/article/creation']);
  }
}
