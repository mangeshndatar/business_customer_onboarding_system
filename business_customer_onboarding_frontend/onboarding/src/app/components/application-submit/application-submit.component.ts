import { Component, inject, Input, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NotificationRequest } from '../../core/models/notifications.model';

@Component({
  selector: 'app-application-submit',
  imports: [MatCardModule],
  templateUrl: './application-submit.component.html',
  styleUrl: './application-submit.component.scss',
})
export class ApplicationSubmitComponent implements OnInit {
  @Input() applicationId!: string;
  readonly dialogRef = inject(MatDialogRef<ApplicationSubmitComponent>);
  readonly data = inject(MAT_DIALOG_DATA);
  router = inject(Router);
  newNotification: NotificationRequest = {
    title: '',
    message: '',
    type: 'INFO',
    userId: '',
  };
  ngOnInit(): void {
    this.applicationId = this.data.name;
  }
  onNoClick(): void {
    console.log('Notification sent successfully' + this.applicationId);
    this.router.navigate(['/processing']);
    this.dialogRef.close();
  }
}
