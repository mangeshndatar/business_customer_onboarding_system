import { Routes } from '@angular/router';
import { OnboardingFormComponent } from './components/onboarding-form/onboarding-form.component';
import { ApplicationDetailsComponent } from './components/application-details/application-details.component';
import { ProcessingDashboardComponent } from './components/processing-dashboard/processing-dashboard.component';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: OnboardingFormComponent },
  { path: 'processing', component: ProcessingDashboardComponent },
  {
    path: 'applications/:id',
    component: ApplicationDetailsComponent,
    pathMatch: 'full',
  },
];
