import { Component } from '@angular/core';
import { Router } from '@angular/router';

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

  private getPreviousUrl(): string {
    window.history.go(-1);
    return '/';
  }
}
