import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/services/session/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  constructor(private sessionsService: SessionService) {}
  title = 'front';

  ngOnInit(): void {
    const token = localStorage.getItem('user_token');
    if (token) {
      this.sessionsService.isLogged = true;
      console.log('you are still connected !');
    } else {
      console.log('session expired, connect again !');
    }
  }
}
