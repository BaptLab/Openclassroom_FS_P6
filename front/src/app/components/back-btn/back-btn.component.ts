import { Component } from '@angular/core';
import { Router, UrlTree } from '@angular/router';

@Component({
  selector: 'app-back-btn',
  templateUrl: './back-btn.component.html',
  styleUrls: ['./back-btn.component.scss'],
})
export class BackBtnComponent {
  constructor(private router: Router) {}

  navigateToPrevious(): void {
    this.router.navigateByUrl(this.getPreviousUrl());
  }

  private getPreviousUrl(): string | UrlTree {
    // Check if there is a previous page
    if (window.history.length > 1) {
      window.history.go(-1);
      return this.router.parseUrl('/'); // Return UrlTree for navigating to the home page
    } else {
      return '/'; // If no previous page, navigate to the home page using string
    }
  }
}
