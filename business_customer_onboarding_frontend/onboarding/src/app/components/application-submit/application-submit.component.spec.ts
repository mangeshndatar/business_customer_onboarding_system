import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationSubmitComponent } from './application-submit.component';
import { MatDialogRef } from '@angular/material/dialog';

describe('ApplicationSubmitComponent', () => {
  let component: ApplicationSubmitComponent;
  let fixture: ComponentFixture<ApplicationSubmitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApplicationSubmitComponent],
      providers: [MatDialogRef],
    }).compileComponents();

    fixture = TestBed.createComponent(ApplicationSubmitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
