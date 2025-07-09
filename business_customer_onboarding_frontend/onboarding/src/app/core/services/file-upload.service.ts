import { Host, Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpEvent,
  HttpHeaders,
  HttpRequest,
  HttpResponse,
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
export interface FileUploadResponse {
  success: boolean;
  message: string;
  fileName: string | null;
  fileDownloadUri: string | null;
}
@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  private apiUrl = 'http://localhost:8081/api/applications';

  constructor(private http: HttpClient) {}

  uploadFile(file: File, applicationId: string): Observable<Document> {
    const formData = new FormData();
    formData.append('files', file);

    return this.http
      .post<Document>(`${this.apiUrl}/${applicationId}/documents`, formData)
      .pipe(catchError(this.handleError));
  }

  getDocumentsByApplication(applicationId: number): Observable<Document[]> {
    return this.http
      .get<Document[]>(`${this.apiUrl}/application/${applicationId}`)
      .pipe(catchError(this.handleError));
  }

  downloadFile(documentId: number): Observable<HttpResponse<Blob>> {
    return this.http
      .get(`${this.apiUrl}/documents/${documentId}/download`, {
        responseType: 'blob',
        observe: 'response', // This allows you to access headers
      })
      .pipe(catchError(this.handleError));
  }

  deleteDocument(documentId: number): Observable<any> {
    return this.http
      .delete(`${this.apiUrl}/${documentId}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
  }
}
