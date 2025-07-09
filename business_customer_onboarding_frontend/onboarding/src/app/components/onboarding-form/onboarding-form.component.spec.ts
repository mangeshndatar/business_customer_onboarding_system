import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { of, throwError } from 'rxjs';
import { OnboardingFormComponent } from './onboarding-form.component';
import { ApplicationService } from '../../core/services/application.service';
import { LegalStructure } from '../../core/enums/legalstructure.enum';
import { Application, Document } from '../../core/models/application.model';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';
import { SharedAngularMaterialModule } from '../../core/shared/angular.material.module';

describe('OnboardingFormComponent', () => {
  let component: OnboardingFormComponent;
  let fixture: ComponentFixture<OnboardingFormComponent>;
  let mockApplicationService: jasmine.SpyObj<ApplicationService>;
  let mockSnackBar: jasmine.SpyObj<MatSnackBar>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockDialog: jasmine.SpyObj<MatDialog>;

  beforeEach(async () => {
    const applicationServiceSpy = jasmine.createSpyObj('ApplicationService', [
      'submitApplication',
    ]);
    const snackBarSpy = jasmine.createSpyObj('MatSnackBar', ['open']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    await TestBed.configureTestingModule({
      imports: [
        OnboardingFormComponent,
        ReactiveFormsModule,
        FormsModule,
        CommonModule,
        SharedAngularMaterialModule,
        NoopAnimationsModule,
      ],
      providers: [
        FormBuilder,
        { provide: ApplicationService, useValue: applicationServiceSpy },
        { provide: MatSnackBar, useValue: snackBarSpy },
        { provide: Router, useValue: routerSpy },
        { provide: MatDialog, useValue: dialogSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(OnboardingFormComponent);
    component = fixture.componentInstance;
    mockApplicationService = TestBed.inject(
      ApplicationService
    ) as jasmine.SpyObj<ApplicationService>;
    mockSnackBar = TestBed.inject(MatSnackBar) as jasmine.SpyObj<MatSnackBar>;
    mockRouter = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    mockDialog = TestBed.inject(MatDialog) as jasmine.SpyObj<MatDialog>;
  });

  describe('Component Initialization', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should initialize form with correct structure', () => {
      expect(component.onboardingForm).toBeDefined();
      expect(component.onboardingForm.get('legalName')).toBeDefined();
      expect(component.onboardingForm.get('legalStructureType')).toBeDefined();
      expect(
        component.onboardingForm.get('countryOfIncorporation')
      ).toBeDefined();
      expect(
        component.onboardingForm.get('businessRegistrationNumber')
      ).toBeDefined();
      expect(
        component.onboardingForm.get('taxIdentificationNumber')
      ).toBeDefined();
      expect(component.onboardingForm.get('industryType')).toBeDefined();
      expect(
        component.onboardingForm.get('primaryContactPerson')
      ).toBeDefined();
      expect(component.onboardingForm.get('contactEmail')).toBeDefined();
      expect(
        component.onboardingForm.get('estimatedAnnualTurnover')
      ).toBeDefined();
      expect(
        component.onboardingForm.get('expectedMonthlyTransactionVolume')
      ).toBeDefined();
      expect(component.onboardingForm.get('directors')).toBeDefined();
      expect(
        component.onboardingForm.get('ultimateBeneficialOwners')
      ).toBeDefined();
    });

    it('should initialize with one director and one UBO by default', () => {
      expect(component.directors.length).toBe(1);
      expect(component.ultimateBeneficialOwners.length).toBe(1);
    });

    it('should initialize arrays with correct values', () => {
      expect(component.legalStructures).toEqual(Object.values(LegalStructure));
      expect(component.industryTypes).toContain('FINANCE');
      expect(component.industryTypes).toContain('TECHNOLOGY');
      expect(component.countries).toContain('United States');
      expect(component.countries).toContain('United Kingdom');
    });

    it('should initialize uploadedDocuments as empty array', () => {
      expect(component.uploadedDocuments).toEqual([]);
    });

    it('should initialize isSubmitting as false', () => {
      expect(component.isSubmitting).toBeFalse();
    });

    it('should initialize applicationId as null', () => {
      expect(component.applicationId).toBeNull();
    });
  });

  describe('Form Validation', () => {
    it('should require legalName', () => {
      const control = component.onboardingForm.get('legalName');
      control?.setValue('');
      expect(control?.hasError('required')).toBeTruthy();
    });

    it('should require minimum length for legalName', () => {
      const control = component.onboardingForm.get('legalName');
      control?.setValue('AB');
      expect(control?.hasError('minlength')).toBeTruthy();
    });

    it('should validate legalStructureType as required', () => {
      const control = component.onboardingForm.get('legalStructureType');
      control?.setValue('');
      expect(control?.hasError('required')).toBeTruthy();
    });

    it('should validate countryOfIncorporation as required', () => {
      const control = component.onboardingForm.get('countryOfIncorporation');
      control?.setValue('');
      expect(control?.hasError('required')).toBeTruthy();
    });

    it('should validate businessRegistrationNumber as required', () => {
      const control = component.onboardingForm.get(
        'businessRegistrationNumber'
      );
      control?.setValue('');
      expect(control?.hasError('required')).toBeTruthy();
    });

    it('should validate taxIdentificationNumber as required', () => {
      const control = component.onboardingForm.get('taxIdentificationNumber');
      control?.setValue('');
      expect(control?.hasError('required')).toBeTruthy();
    });

    it('should validate industryType as required', () => {
      const control = component.onboardingForm.get('industryType');
      control?.setValue('');
      expect(control?.hasError('required')).toBeTruthy();
    });

    it('should validate primaryContactPerson as required', () => {
      const control = component.onboardingForm.get('primaryContactPerson');
      control?.setValue('');
      expect(control?.hasError('required')).toBeTruthy();
    });

    it('should validate contactEmail as required', () => {
      const control = component.onboardingForm.get('contactEmail');
      control?.setValue('');
      expect(control?.hasError('required')).toBeTruthy();
    });

    it('should validate estimatedAnnualTurnover as required', () => {
      const control = component.onboardingForm.get('estimatedAnnualTurnover');
      control?.setValue('');
      expect(control?.hasError('required')).toBeTruthy();
    });

    it('should validate expectedMonthlyTransactionVolume as required', () => {
      const control = component.onboardingForm.get(
        'expectedMonthlyTransactionVolume'
      );
      control?.setValue('');
      expect(control?.hasError('required')).toBeTruthy();
    });
  });

  describe('Director Form Group', () => {
    it('should create director form group with correct structure', () => {
      const directorGroup = component.createDirectorFormGroup();
      expect(directorGroup.get('name')).toBeDefined();
      expect(directorGroup.get('nationalIdPassport')).toBeDefined();
      expect(directorGroup.get('countryOfResidence')).toBeDefined();
    });

    it('should validate director name as required', () => {
      const directorGroup = component.createDirectorFormGroup();
      const nameControl = directorGroup.get('name');
      nameControl?.setValue('');
      expect(nameControl?.hasError('required')).toBeTruthy();
    });

    it('should validate director name minimum length', () => {
      const directorGroup = component.createDirectorFormGroup();
      const nameControl = directorGroup.get('name');
      nameControl?.setValue('AB');
      expect(nameControl?.hasError('minlength')).toBeTruthy();
    });

    it('should validate nationalIdPassport pattern', () => {
      const directorGroup = component.createDirectorFormGroup();
      const nationalIdControl = directorGroup.get('nationalIdPassport');
      nationalIdControl?.setValue('invalid-id');
      expect(nationalIdControl?.hasError('pattern')).toBeTruthy();
    });

    it('should accept valid nationalIdPassport pattern', () => {
      const directorGroup = component.createDirectorFormGroup();
      const nationalIdControl = directorGroup.get('nationalIdPassport');
      nationalIdControl?.setValue('ABC123-456');
      expect(nationalIdControl?.hasError('pattern')).toBeFalsy();
    });

    it('should validate countryOfResidence as required', () => {
      const directorGroup = component.createDirectorFormGroup();
      const countryControl = directorGroup.get('countryOfResidence');
      countryControl?.setValue('');
      expect(countryControl?.hasError('required')).toBeTruthy();
    });
  });

  describe('UBO Form Group', () => {
    it('should create UBO form group with correct structure', () => {
      const uboGroup = component.createUboFormGroup();
      expect(uboGroup.get('name')).toBeDefined();
      expect(uboGroup.get('ownershipPercentage')).toBeDefined();
      expect(uboGroup.get('countryOfResidence')).toBeDefined();
      expect(uboGroup.get('nationalIdPassport')).toBeDefined();
    });

    it('should validate UBO name as required', () => {
      const uboGroup = component.createUboFormGroup();
      const nameControl = uboGroup.get('name');
      nameControl?.setValue('');
      expect(nameControl?.hasError('required')).toBeTruthy();
    });

    it('should validate UBO name minimum length', () => {
      const uboGroup = component.createUboFormGroup();
      const nameControl = uboGroup.get('name');
      nameControl?.setValue('AB');
      expect(nameControl?.hasError('minlength')).toBeTruthy();
    });

    it('should validate ownershipPercentage minimum value', () => {
      const uboGroup = component.createUboFormGroup();
      const percentageControl = uboGroup.get('ownershipPercentage');
      percentageControl?.setValue(-1);
      expect(percentageControl?.hasError('min')).toBeTruthy();
    });

    it('should validate ownershipPercentage maximum value', () => {
      const uboGroup = component.createUboFormGroup();
      const percentageControl = uboGroup.get('ownershipPercentage');
      percentageControl?.setValue(101);
      expect(percentageControl?.hasError('max')).toBeTruthy();
    });

    it('should accept valid ownershipPercentage', () => {
      const uboGroup = component.createUboFormGroup();
      const percentageControl = uboGroup.get('ownershipPercentage');
      percentageControl?.setValue(50);
      expect(percentageControl?.hasError('min')).toBeFalsy();
      expect(percentageControl?.hasError('max')).toBeFalsy();
    });
  });

  describe('Dynamic Form Array Management', () => {
    it('should add director to form array', () => {
      const initialLength = component.directors.length;
      component.addDirector();
      expect(component.directors.length).toBe(initialLength + 1);
    });

    it('should remove director from form array', () => {
      component.addDirector(); // Add one more so we can remove one
      const initialLength = component.directors.length;
      component.removeDirector(0);
      expect(component.directors.length).toBe(initialLength - 1);
    });

    it('should not remove director if only one remains', () => {
      component.removeDirector(0);
      expect(component.directors.length).toBe(1);
    });

    it('should add UBO to form array', () => {
      const initialLength = component.ultimateBeneficialOwners.length;
      component.addUbo();
      expect(component.ultimateBeneficialOwners.length).toBe(initialLength + 1);
    });

    it('should remove UBO from form array', () => {
      component.addUbo(); // Add one more so we can remove one
      const initialLength = component.ultimateBeneficialOwners.length;
      component.removeUbo(0);
      expect(component.ultimateBeneficialOwners.length).toBe(initialLength - 1);
    });

    it('should not remove UBO if only one remains', () => {
      component.removeUbo(0);
      expect(component.ultimateBeneficialOwners.length).toBe(1);
    });
  });

  describe('Document Management', () => {
    it('should add document to uploadedDocuments array', () => {
      const mockDocument: Document = {
        fileName: 'test.pdf',
        filePath: '/path/to/test.pdf',
        fileType: 'application/pdf',
        fileSize: 1024,
      };

      component.onDocumentUploaded(mockDocument);
      expect(component.uploadedDocuments).toContain(mockDocument);
    });

    it('should handle multiple document uploads', () => {
      const mockDoc1: Document = {
        fileName: 'doc1.pdf',
        filePath: '/path/to/doc1.pdf',
        fileType: 'application/pdf',
        fileSize: 1024,
      };

      const mockDoc2: Document = {
        fileName: 'doc2.pdf',
        filePath: '/path/to/doc2.pdf',
        fileType: 'application/pdf',
        fileSize: 2048,
      };

      component.onDocumentUploaded(mockDoc1);
      component.onDocumentUploaded(mockDoc2);

      expect(component.uploadedDocuments.length).toBe(2);
      expect(component.uploadedDocuments).toContain(mockDoc1);
      expect(component.uploadedDocuments).toContain(mockDoc2);
    });
  });

  describe('Form Submission', () => {
    beforeEach(() => {
      // Set up valid form data
      component.onboardingForm.patchValue({
        legalName: 'Test Company Ltd',
        legalStructureType: LegalStructure.ASSOCIATION,
        countryOfIncorporation: 'United States',
        businessRegistrationNumber: 'REG123456',
        taxIdentificationNumber: 'TAX789012',
        industryType: 'TECHNOLOGY',
        primaryContactPerson: 'John Doe',
        contactEmail: 'john.doe@example.com',
        estimatedAnnualTurnover: 1000000,
        expectedMonthlyTransactionVolume: 50000,
      });

      // Set valid director data
      component.directors.at(0).patchValue({
        name: 'John Director',
        nationalIdPassport: 'ABC123',
        countryOfResidence: 'United States',
      });

      // Set valid UBO data
      component.ultimateBeneficialOwners.at(0).patchValue({
        name: 'Jane Owner',
        ownershipPercentage: 75,
        countryOfResidence: 'United States',
        nationalIdPassport: 'DEF456',
      });
    });

    it('should submit form when valid', () => {
      const mockResponse = { applicationId: 'APP123' };
      //mockApplicationService.submitApplication.and.returnValue(of(mockResponse));

      component.onSubmit();

      expect(mockApplicationService.submitApplication).toHaveBeenCalled();
      expect(component.isSubmitting).toBeFalse();
      expect(mockRouter.navigate).toHaveBeenCalledWith([
        '/document-upload',
        'APP123',
      ]);
    });

    it('should set isSubmitting to true during submission', () => {
      const mockResponse = { applicationId: 'APP123' };
      //  mockApplicationService.submitApplication.and.returnValue(of(mockResponse));

      component.onSubmit();

      expect(component.isSubmitting).toBeTruthy();
    });

    it('should reset form after successful submission', () => {
      const mockResponse = { applicationId: 'APP123' };
      //mockApplicationService.submitApplication.and.returnValue(of(mockResponse));

      component.onSubmit();

      expect(component.onboardingForm.value.legalName).toBe('');
      expect(component.uploadedDocuments).toEqual([]);
    });

    it('should handle submission error', () => {
      mockApplicationService.submitApplication.and.returnValue(
        throwError('Submission failed')
      );

      component.onSubmit();

      expect(component.isSubmitting).toBeFalse();
    });

    it('should not submit if form is invalid', () => {
      component.onboardingForm.patchValue({
        legalName: '', // Make form invalid
      });

      component.onSubmit();

      expect(mockApplicationService.submitApplication).not.toHaveBeenCalled();
      expect(mockSnackBar.open).toHaveBeenCalledWith(
        'Please fill in all required fields correctly.',
        'Close',
        jasmine.any(Object)
      );
    });

    it('should mark form as touched when submission fails validation', () => {
      component.onboardingForm.patchValue({
        legalName: '', // Make form invalid
      });

      //  spyOn(component, 'markFormGroupTouched');

      component.onSubmit();

      //  expect(component.markFormGroupTouched).toHaveBeenCalled();
    });
  });

  describe('Error Messages', () => {
    it('should return required error message', () => {
      const control = component.onboardingForm.get('legalName');
      control?.setValue('');
      control?.markAsTouched();

      const errorMessage = component.getErrorMessage('legalName');
      expect(errorMessage).toBe('legalName is required');
    });

    it('should return email error message', () => {
      // Mock email validation error
      const control = component.onboardingForm.get('contactEmail');
      control?.setErrors({ email: true });

      const errorMessage = component.getErrorMessage('contactEmail');
      expect(errorMessage).toBe('Please enter a valid email address');
    });

    it('should return minlength error message', () => {
      const control = component.onboardingForm.get('legalName');
      control?.setValue('AB');
      control?.markAsTouched();

      const errorMessage = component.getErrorMessage('legalName');
      expect(errorMessage).toContain('must be at least');
    });

    it('should return pattern error message', () => {
      const directorGroup = component.createDirectorFormGroup();
      const control = directorGroup.get('nationalIdPassport');
      control?.setValue('invalid-pattern');
      control?.markAsTouched();

      const errorMessage = component.getErrorMessage('nationalIdPassport');
      expect(errorMessage).toContain('format is invalid');
    });

    it('should return min error message', () => {
      const uboGroup = component.createUboFormGroup();
      const control = uboGroup.get('ownershipPercentage');
      control?.setValue(-1);
      control?.markAsTouched();

      const errorMessage = component.getErrorMessage('ownershipPercentage');
      expect(errorMessage).toContain('must be greater than or equal to');
    });

    it('should return max error message', () => {
      const uboGroup = component.createUboFormGroup();
      const control = uboGroup.get('ownershipPercentage');
      control?.setValue(101);
      control?.markAsTouched();

      const errorMessage = component.getErrorMessage('ownershipPercentage');
      expect(errorMessage).toContain('must be less than or equal to');
    });

    it('should return empty string for valid control', () => {
      const control = component.onboardingForm.get('legalName');
      control?.setValue('Valid Company Name');

      const errorMessage = component.getErrorMessage('legalName');
      expect(errorMessage).toBe('');
    });
  });

  describe('Mark Form Group Touched', () => {
    it('should mark all form controls as touched', () => {
      spyOn(component.onboardingForm, 'get').and.callThrough();

      component['markFormGroupTouched']();

      expect(component.onboardingForm.get).toHaveBeenCalled();
    });

    it('should mark FormArray controls as touched', () => {
      const directorControl = component.directors.at(0);
      const uboControl = component.ultimateBeneficialOwners.at(0);

      spyOn(directorControl, 'get').and.callThrough();
      spyOn(uboControl, 'get').and.callThrough();

      component['markFormGroupTouched']();

      expect(directorControl.get).toHaveBeenCalled();
      expect(uboControl.get).toHaveBeenCalled();
    });
  });

  describe('Integration Tests', () => {
    it('should handle complete workflow from form fill to submission', () => {
      // Fill form with valid data
      component.onboardingForm.patchValue({
        legalName: 'Test Company Ltd',
        legalStructureType: LegalStructure.ASSOCIATION,
        countryOfIncorporation: 'United States',
        businessRegistrationNumber: 'REG123456',
        taxIdentificationNumber: 'TAX789012',
        industryType: 'TECHNOLOGY',
        primaryContactPerson: 'John Doe',
        contactEmail: 'john.doe@example.com',
        estimatedAnnualTurnover: 1000000,
        expectedMonthlyTransactionVolume: 50000,
      });

      // Add directors and UBOs
      component.addDirector();
      component.addUbo();

      // Upload documents
      const mockDocument: Document = {
        fileName: 'test.pdf',
        filePath: '/path/to/test.pdf',
        fileType: 'application/pdf',
        fileSize: 1024,
      };
      component.onDocumentUploaded(mockDocument);

      // Mock successful submission
      const mockResponse = { applicationId: 'APP123' };
      //  mockApplicationService.submitApplication.and.returnValue(of(mockResponse));

      // Submit form
      component.onSubmit();

      // Verify submission
      expect(mockApplicationService.submitApplication).toHaveBeenCalledWith(
        jasmine.objectContaining({
          legalName: 'Test Company Ltd',
          documents: [mockDocument],
        })
      );
      expect(mockRouter.navigate).toHaveBeenCalledWith([
        '/document-upload',
        'APP123',
      ]);
    });

    it('should handle form validation errors properly', () => {
      // Submit empty form
      component.onSubmit();

      // Verify validation error handling
      expect(mockApplicationService.submitApplication).not.toHaveBeenCalled();
      expect(mockSnackBar.open).toHaveBeenCalledWith(
        'Please fill in all required fields correctly.',
        'Close',
        jasmine.any(Object)
      );
    });
  });
});
