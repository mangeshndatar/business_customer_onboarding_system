import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { Stomp, CompatClient } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient: CompatClient | null = null;
  private notificationSubject = new Subject<any>();
  private serverUrl = 'http://localhost:8081/api/ws';

  constructor() {}

  connect(): void {
    if (this.stompClient && this.stompClient.connected) {
      return;
    }

    const socket = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(socket);

    // Disable console logging for cleaner output
    this.stompClient.debug = () => {};

    this.stompClient.connect(
      {},
      (frame: any) => {
        console.log('Connected to WebSocket: ' + frame);
        this.subscribeToNotifications();
      },
      (error: any) => {
        console.error('WebSocket connection error: ', error);
        // Retry connection after 5 seconds
        setTimeout(() => this.connect(), 5000);
      }
    );
  }

  private subscribeToNotifications(): void {
    if (this.stompClient) {
      this.stompClient.subscribe('/topic/notifications', (message) => {
        const notification = JSON.parse(message.body);
        this.notificationSubject.next(notification);
      });
    }
  }

  getNotifications(): Observable<any> {
    return this.notificationSubject.asObservable();
  }

  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect();
      this.stompClient = null;
    }
  }
}
