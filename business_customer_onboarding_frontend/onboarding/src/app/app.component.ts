import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { ApplicationService } from './core/services/application.service';
import { WebSocketService } from './core/services/web-socket.service';
import { Subscription } from 'rxjs';
import { CommonModule, DatePipe } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { MatBadge } from '@angular/material/badge';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    FormsModule,
    MatToolbarModule,
    RouterModule,
    MatButtonModule,
    CommonModule,
    MatIcon,
    MatBadge,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit, OnDestroy {
  private notificationSubscription: Subscription = new Subscription();
  latestApplication: any[] = [];
  applicationId: number = 0;
  constructor(
    private applicationService: ApplicationService,
    private webSocketService: WebSocketService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.connectWebSocket();
  }

  ngOnDestroy() {
    this.notificationSubscription.unsubscribe();
    this.webSocketService.disconnect();
  }

  connectWebSocket() {
    this.webSocketService.connect();

    this.notificationSubscription = this.webSocketService
      .getNotifications()
      .subscribe(
        (notification: any) => {
          console.log('Received notification:', notification);
          this.showNotification(notification);
          if (this.applicationId > 0) {
            this.loadLatestApplication();
          }
        },
        (error) => {
          console.error('WebSocket error:', error);
        }
      );
  }
  loadLatestApplication() {
    this.applicationService.getApplicationById(this.applicationId).subscribe(
      (response) => {
        // this.latestApplication = response;
      },
      (error) => {
        console.error('Error loading latest application:', error);
      }
    );
  }
  showNotification(notification: any) {
    this.latestApplication.push(notification);
    this.toastr.info(
      `Application ${notification.applicationId} is now ${notification.status}`,
      'Application Update',
      {
        timeOut: 5000,
        closeButton: true,
        progressBar: true,
      }
    );
  }
}
