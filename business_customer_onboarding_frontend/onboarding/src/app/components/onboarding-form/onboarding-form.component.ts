import { Component, inject } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { LegalStructure } from '../../core/enums/legalstructure.enum';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ApplicationService } from '../../core/services/application.service';
import { Application } from '../../core/models/application.model';
import { MatCard, MatCardHeader, MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { CommonModule } from '@angular/common';
import { SharedAngularMaterialModule } from '../../core/shared/angular.material.module';
import { Document } from '../../core/models/application.model';
import { FileUploadComponent } from '../file-upload/file-upload.component';
import { ApplicationSubmitComponent } from '../application-submit/application-submit.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-onboarding-form',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    SharedAngularMaterialModule,
    FileUploadComponent,
  ],
  templateUrl: './onboarding-form.component.html',
  styleUrl: './onboarding-form.component.scss',
})
export class OnboardingFormComponent {
  onboardingForm: FormGroup;
  legalStructures = Object.values(LegalStructure);
  industryTypes = [
    'FINANCE',
    'REAL_ESTATE',
    'SERVICES',
    'MANUFACTURING',
    'TECHNOLOGY',
    'HEALTHCARE',
    'EDUCATION',
    'RETAIL',
    'AGRICULTURE',
    'CONSTRUCTION',
    'TRANSPORTATION',
    'OTHER',
  ];
  countries = [
    'United States',
    'United Kingdom',
    'Canada',
    'Australia',
    'Singapore',
    'Hong Kong',
    'Germany',
    'France',
  ];
  uploadedDocuments: Document[] = [];
  isSubmitting = false;
  applicationId: string | null = null;
  readonly dialog = inject(MatDialog);

  constructor(
    private fb: FormBuilder,
    private applicationService: ApplicationService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.onboardingForm = this.createForm();
  }
  createForm(): FormGroup {
    return this.fb.group({
      legalName: ['', [Validators.required, Validators.minLength(3)]],
      legalStructureType: ['', Validators.required],
      countryOfIncorporation: ['', Validators.required],
      businessRegistrationNumber: ['', [Validators.required]],
      taxIdentificationNumber: ['', [Validators.required]],
      industryType: ['', Validators.required],
      primaryContactPerson: ['', [Validators.required]],
      contactEmail: ['', [Validators.required]],
      estimatedAnnualTurnover: ['', [Validators.required]],
      expectedMonthlyTransactionVolume: ['', [Validators.required]],
      directors: this.fb.array([this.createDirectorFormGroup()]),
      ultimateBeneficialOwners: this.fb.array([this.createUboFormGroup()]),
    });
  }

  createDirectorFormGroup(): FormGroup {
    return this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      nationalIdPassport: [
        '',
        [Validators.required, Validators.pattern(/^[A-Z0-9-]+$/)],
      ],
      countryOfResidence: ['', Validators.required],
    });
  }

  createUboFormGroup(): FormGroup {
    return this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      ownershipPercentage: [
        '',
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      countryOfResidence: ['', Validators.required],
      nationalIdPassport: ['', Validators.required],
    });
  }

  get directors(): FormArray {
    return this.onboardingForm.get('directors') as FormArray;
  }

  get ultimateBeneficialOwners(): FormArray {
    return this.onboardingForm.get('ultimateBeneficialOwners') as FormArray;
  }

  addDirector(): void {
    this.directors.push(this.createDirectorFormGroup());
  }

  removeDirector(index: number): void {
    if (this.directors.length > 1) {
      this.directors.removeAt(index);
    }
  }

  addUbo(): void {
    this.ultimateBeneficialOwners.push(this.createUboFormGroup());
  }

  removeUbo(index: number): void {
    if (this.ultimateBeneficialOwners.length > 1) {
      this.ultimateBeneficialOwners.removeAt(index);
    }
  }

  onDocumentUploaded(document: Document): void {
    this.uploadedDocuments.push(document);
  }

  onSubmit(): void {
    if (this.onboardingForm.valid) {
      this.isSubmitting = true;

      const application: Application = {
        ...this.onboardingForm.value,
        documents: this.uploadedDocuments,
      };
      this.applicationService.submitApplication(application).subscribe({
        next: (response) => {
          this.applicationId = response.applicationId!;
          // this.snackBar.open(
          //   `Application submitted successfully! Application ID: ${this.applicationId}`,
          //   'Close',
          //   {
          //     duration: 10000,
          //     panelClass: ['success-snackbar'],
          //   }
          // );
          this.onboardingForm.reset();
          this.uploadedDocuments = [];
          this.isSubmitting = false;
          this.openDialog();
        },
        error: (error) => {
          // this.snackBar.open(
          //   'Failed to submit application. Please try again.',
          //   'Close',
          //   {
          //     duration: 5000,
          //     panelClass: ['error-snackbar'],
          //   }
          // );
          this.isSubmitting = false;
        },
      });
    } else {
      this.markFormGroupTouched();
      this.snackBar.open(
        'Please fill in all required fields correctly.',
        'Close',
        {
          duration: 5000,
          panelClass: ['error-snackbar'],
        }
      );
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.onboardingForm.controls).forEach((key) => {
      const control = this.onboardingForm.get(key);
      control?.markAsTouched();

      if (control instanceof FormArray) {
        control.controls.forEach((ctrl: any) => {
          Object.keys(ctrl.controls).forEach((innerKey) => {
            ctrl.get(innerKey)?.markAsTouched();
          });
        });
      }
    });
  }

  getErrorMessage(controlName: string): string {
    const control = this.onboardingForm.get(controlName);
    if (control?.hasError('required')) {
      return `${controlName} is required`;
    }
    if (control?.hasError('email')) {
      return 'Please enter a valid email address';
    }
    if (control?.hasError('minlength')) {
      return `${controlName} must be at least ${control.errors?.['minlength'].requiredLength} characters`;
    }
    if (control?.hasError('pattern')) {
      return `${controlName} format is invalid`;
    }
    if (control?.hasError('min')) {
      return `${controlName} must be greater than or equal to ${control.errors?.['min'].min}`;
    }
    if (control?.hasError('max')) {
      return `${controlName} must be less than or equal to ${control.errors?.['max'].max}`;
    }
    return '';
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(ApplicationSubmitComponent, {
      data: { name: this.applicationId },
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      console.log('The dialog was closed');
      this.router.navigate(['/document-upload']);
      if (result !== undefined) {
      }
    });
  }
}
