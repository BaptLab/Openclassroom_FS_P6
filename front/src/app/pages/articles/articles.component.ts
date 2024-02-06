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
  sortBoolean: boolean = true;
  constructor(private articleService: ArticleService, private router: Router) {}

  ngOnInit(): void {
    const userId = localStorage.getItem('user_id');
    this.articleService.getArticles(userId).subscribe(
      (receivedArticles: Article[]) => {
        this.articles = receivedArticles;
        this.sortArticle();
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

  sortArticle(): void {
    this.sortBoolean = !this.sortBoolean;
    if (this.sortBoolean) {
      this.articles.sort((a, b) => {
        return (
          new Date(a.updatedAt).getTime() - new Date(b.updatedAt).getTime()
        );
      });
    } else {
      this.articles.sort((a, b) => {
        return (
          new Date(b.updatedAt).getTime() - new Date(a.updatedAt).getTime()
        );
      });
    }
  }
}
