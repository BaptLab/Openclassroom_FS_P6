import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from 'src/services/session/session.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  constructor(public sessionService: SessionService, private router: Router) {}

  ngOnInit(): void {}

  navigateToArticles(): void {
    this.router.navigate(['/articles']);
  }

  navigateToThemes(): void {
    this.router.navigate(['/themes']);
  }

  navigateToProfile(): void {
    this.router.navigate(['/profile']);
  }

  isActive(routes: string[]): boolean {
    return routes.some((route) => this.router.isActive(route, true));
  }
}
