import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { SharedAngularMaterialModule } from '../../core/shared/angular.material.module';
import { MatChip } from '@angular/material/chips';
import { Application } from '../../core/models/application.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { ApplicationService } from '../../core/services/application.service';
import { MatListModule } from '@angular/material/list';
import { ApplicationStatus } from '../../core/enums/legalstructure.enum';
import { FileUploadService } from '../../core/services/file-upload.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-application-details',
  imports: [
    CommonModule,
    FormsModule,
    SharedAngularMaterialModule,
    MatChip,
    ReactiveFormsModule,
    MatListModule,
  ],
  templateUrl: './application-details.component.html',
  styleUrl: './application-details.component.scss',
})
export class ApplicationDetailsComponent {
  application!: Application;
  applicationForm!: FormGroup;
  isEditMode = false;
  processingNotes = '';
  checkRequired = false;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private applicationService: ApplicationService,
    private snackBar: MatSnackBar,
    private fileUpload: FileUploadService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    const editMode = this.route.snapshot.queryParams['edit'] === 'true';

    this.applicationService
      .getApplicationByApplicationId(id)
      .subscribe((res: any) => {
        this.application = res;
        if (this.application) {
          this.initializeForm();
          this.isEditMode = editMode;
        }
      });
  }

  private initializeForm() {
    if (!this.application) return;

    this.applicationForm = this.fb.group({
      legalName: [this.application.legalName, Validators.required],
      legalStructureType: [
        this.application.legalStructureType,
        Validators.required,
      ],
      countryOfIncorporation: [
        this.application.countryOfIncorporation,
        Validators.required,
      ],
      businessRegistrationNumber: [
        this.application.businessRegistrationNumber,
        Validators.required,
      ],
      taxId: [this.application.taxIdentificationNumber, Validators.required],
      industryType: [this.application.industryType, Validators.required],
      primaryContact: [
        this.application.primaryContactPerson,
        Validators.required,
      ],
      contactEmail: [
        this.application.contactEmail,
        [Validators.required, Validators.email],
      ],
      estimatedAnnualTurnover: [this.application.estimatedAnnualTurnover],
    });
  }

  toggleEditMode() {
    this.isEditMode = !this.isEditMode;
  }

  cancelEdit() {
    this.isEditMode = false;
    this.initializeForm(); // Reset form to original values
  }

  saveChanges() {
    if (this.applicationForm.valid && this.application && this.application.id) {
      const updates = this.applicationForm.value;
      this.applicationService.updateApplication(this.application.id, updates);
      this.application = { ...this.application, ...updates };
      this.isEditMode = false;
      this.snackBar.open('Application updated successfully', 'Close', {
        duration: 3000,
      });
    }
  }

  approveApplication() {
    if (this.processingNotes.length == 0) {
      this.checkRequired = true;
      return;
    } else if (this.application && this.application.id) {
      this.updateTheStatus(this.application, ApplicationStatus.APPROVED);
    }
  }

  rejectApplication() {
    if (this.processingNotes.length == 0) {
      this.checkRequired = true;
      return;
    } else if (this.application && this.application.id) {
      this.updateTheStatus(this.application, ApplicationStatus.REJECTED);
    }
  }

  viewDocument(id: any) {
    console.log('doc id', id);
    this.fileUpload
      .downloadFile(id)
      .subscribe((response: HttpResponse<Blob>) => {
        const blob = response.body;
        if (blob) {
          const contentDisposition = response.headers.get(
            'Content-Disposition'
          );
          let filename = `document_${id}`;

          if (contentDisposition) {
            const filenameMatch = contentDisposition.match(/filename="(.+)"/);
            if (filenameMatch) {
              filename = filenameMatch[1];
            }
          }

          const blobUrl = URL.createObjectURL(blob);
          window.open(blobUrl, '_blank');

          setTimeout(() => {
            URL.revokeObjectURL(blobUrl);
          }, 100);
        }
      });
    // window.open(url, '_blank');
  }

  updateTheStatus(application: Application, status: ApplicationStatus) {
    if (application && application.id && application.status) {
      this.applicationService
        .processApplication(application.id, status, {
          notes: this.processingNotes,
          decision: status,
        })
        .subscribe((res: any) => {
          console.log(res);

          if (res) {
            this.application.status = status;
            this.snackBar.open(`Application ${status} successfully`, 'Close', {
              duration: 3000,
              panelClass: ['success-snackbar'],
            });
          }
          this.back();
        });
    }
  }
  back() {
    this.router.navigate(['/processing']);
  }
}
