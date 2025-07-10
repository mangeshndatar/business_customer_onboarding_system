import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApplicationEvent } from './core/models/application.event';
import { StoreObj } from './core/models/store.model';
import { StoreObjKey } from './core/models/sharedobj.model';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  private data: BehaviorSubject<StoreObj> = new BehaviorSubject<StoreObj>({
    applicationId: '',
    status: '',
  });
  currentData$: Observable<StoreObj> = this.data.asObservable();

  private notifications: BehaviorSubject<ApplicationEvent[]> =
    new BehaviorSubject<ApplicationEvent[]>([]);
  currentNotificationsData$: Observable<ApplicationEvent[]> =
    this.notifications.asObservable();

  saveNotificationData(applicationEvents: ApplicationEvent[]) {
    this.notifications.next(applicationEvents);
  }
  setSharedDataToDefault() {
    this.data = new BehaviorSubject<StoreObj>({
      applicationId: '',
      status: '',
    });
  }

  updateSharedData(keyName: StoreObjKey, value: boolean | string | number) {
    const currentValue = this.data.value;
    console.log('updateSharedData()');
    console.log('current', currentValue);
    const updated: StoreObj = {
      ...currentValue,
      [keyName]: value,
    };
    console.log(keyName + '::' + value);
    this.data.next(updated);
  }

  getMessages(): Observable<any> {
    return this.data.asObservable();
  }
}
