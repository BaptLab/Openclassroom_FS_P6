import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentService } from 'src/services/HttpRequests/comments.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss'],
})
export class CommentComponent implements OnInit {
  constructor() {}

  @Input() comment: any = {};
  @Input() author: string | null = '';

  ngOnInit(): void {}
}
