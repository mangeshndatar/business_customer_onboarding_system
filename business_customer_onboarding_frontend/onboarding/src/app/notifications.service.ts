import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  Notification,
  NotificationRequest,
} from '../app/core/models/notifications.model';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private apiUrl = 'http://localhost:8081/api/notifications';

  constructor(private http: HttpClient) {}

  sendNotification(request: NotificationRequest): Observable<Notification> {
    return this.http.post<Notification>(`${this.apiUrl}/send`, request);
  }

  broadcastNotification(
    request: NotificationRequest
  ): Observable<Notification> {
    return this.http.post<Notification>(`${this.apiUrl}/broadcast`, request);
  }
}
