import { Injectable } from '@angular/core';
import { Client, Message, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Notification } from '../app/core/models/notifications.model';
import { ApplicationEvent } from './core/models/application.event';
export interface StoreObj {
  applicationId: string;
  status: string;
}
@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient?: Client;
  private subject = new Subject<any>();

  constructor() {
    // this.stompClient = new Client({
    //   webSocketFactory: () => new SockJS('http://localhost:8081/api/ws'),
    //   reconnectDelay: 8000,
    //   debug: (str) => console.log(str),
    // });
    // // Set up connection and subscription
    // this.stompClient.onConnect = (frame) => {
    //   this.stompClient.subscribe('/topic/applicants', (message: IMessage) => {
    //     this.subject.next(JSON.parse(message.body));
    //   });
    // };
    // this.stompClient.activate();
  }

  getMessages(): Observable<any> {
    return this.subject.asObservable();
  }
}
