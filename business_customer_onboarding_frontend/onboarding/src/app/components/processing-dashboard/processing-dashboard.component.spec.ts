import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessingDashboardComponent } from './processing-dashboard.component';

describe('ProcessingDashboardComponent', () => {
  let component: ProcessingDashboardComponent;
  let fixture: ComponentFixture<ProcessingDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProcessingDashboardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProcessingDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
