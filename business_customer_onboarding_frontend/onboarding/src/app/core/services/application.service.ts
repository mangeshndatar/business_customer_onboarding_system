import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BusinessApplication } from '../models/business-application.model';
import { Observable } from 'rxjs';
import { Application, Response } from '../models/application.model';
import { ApplicationStatus } from '../enums/legalstructure.enum';

@Injectable({ providedIn: 'root' })
export class ApplicationService {
  private apiUrl = 'http://localhost:8081/api/applications';
  private processing = 'http://localhost:8081/api/processing';

  constructor(private http: HttpClient) {}

  submitApplication(application: Application): Observable<Application> {
    return this.http.post<Application>(this.apiUrl, application);
  }

  getAllApplications(): Observable<Response> {
    return this.http.get<Response>(this.apiUrl);
  }

  getApplicationById(id: number): Observable<Application> {
    return this.http.get<Application>(`${this.apiUrl}/${id}`);
  }

  getApplicationByApplicationId(
    applicationId: string
  ): Observable<Application> {
    return this.http.get<Application>(
      `${this.apiUrl}/application-id/${applicationId}`
    );
  }

  updateApplication(
    id: number,
    application: Application
  ): Observable<Application> {
    console.log('service payload', application);
    return this.http.put<Application>(`${this.apiUrl}/${id}`, application);
  }

  processApplication(
    id: number,
    status: string,
    processing: {
      notes: string;
      decision: ApplicationStatus;
    }
  ): Observable<Application> {
    return this.http.post<Application>(
      `${this.processing}/applications/${id}/process?status=${status}`,
      processing
    );
  }
  getLatestApplication(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/latest`);
  }
}
