import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/services/session/session.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  constructor(public sessionService: SessionService) {}

  ngOnInit(): void {}
}
