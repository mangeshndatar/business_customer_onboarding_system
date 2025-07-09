import { Component, inject, Input, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';

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
  ngOnInit(): void {
    this.applicationId = this.data.name;
  }
  onNoClick(): void {
    this.router.navigate(['/processing']);
    this.dialogRef.close();
  }
}
