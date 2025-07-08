import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SharedAngularMaterialModule } from '../../core/shared/angular.material.module';
import { MatTableDataSource } from '@angular/material/table';
import { Router, RouterModule } from '@angular/router';
import { Application, Response } from '../../core/models/application.model';
import { ApplicationService } from '../../core/services/application.service';
import { MatChip } from '@angular/material/chips';
import { MatIcon } from '@angular/material/icon';
import { MatBadge } from '@angular/material/badge';

@Component({
  selector: 'app-processing-dashboard',
  imports: [
    FormsModule,
    CommonModule,
    SharedAngularMaterialModule,
    MatChip,
    MatIcon,
    MatBadge,
    RouterModule,
  ],
  templateUrl: './processing-dashboard.component.html',
  styleUrl: './processing-dashboard.component.scss',
})
export class ProcessingDashboardComponent {
  applications?: Response;
  messages: string[] = [];
  dataSource = new MatTableDataSource<Application>();

  displayedColumns: string[] = [
    'id',
    'legalName',
    'legalStructure',
    'countryOfIncorporation',
    'businessRegistrationNumber',
    'industryType',
    'status',
    'submissionDate',
    'actions',
  ];

  totalApplications: number | undefined = 0;
  pendingApplications: number | undefined = 0;
  approvedApplications: number | undefined = 0;
  rejectedApplications: number | undefined = 0;

  constructor(
    private applicationService: ApplicationService,
    private router: Router
  ) {}

  ngOnInit() {
    this.applicationService
      .getAllApplications()
      .subscribe((applications: Response) => {
        this.applications = applications;
        this.dataSource.data = applications.content;
        this.updateStats();
      });

    // this.applicationService.getMessages().subscribe(messages => {
    //   this.messages = messages;
    // });
  }

  private updateStats() {
    this.totalApplications = this.applications?.totalElements;
    this.pendingApplications = this.applications?.content.filter(
      (app: Application) => app.status === 'PENDING'
    ).length;
    this.approvedApplications = this.applications?.content.filter(
      (app: any) => app.status === 'APPROVED'
    ).length;
    this.rejectedApplications = this.applications?.content.filter(
      (app: any) => app.status === 'REJECTED'
    ).length;
  }

  viewApplication(id: string) {
    console.log('id', id);
    this.router.navigate(['/applications/', id]);
  }

  editApplication(id: string) {
    this.router.navigate(['/applications', id], {
      // queryParams: { edit: 'true' },
    });
  }

  submitApplication(id: any) {}
}
