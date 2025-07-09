import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { FileUploadService } from '../../core/services/file-upload.service';
import { CommonModule, DatePipe } from '@angular/common';
import { ApplicationSubmitComponent } from '../application-submit/application-submit.component';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
export interface Document {
  id: number;
  applicationId: number;
  fileName: string;
  originalFileName: string;
  filePath: string;
  fileSize: number;
  contentType: string;
  uploadedAt: string;
}

@Component({
  selector: 'app-document-upload',
  imports: [DatePipe, CommonModule],
  templateUrl: './document-upload.component.html',
  styleUrl: './document-upload.component.scss',
})
export class DocumentUploadComponent {
  @Input() applicationId: string = '';
  @Output() fileUploaded = new EventEmitter<Document>();
  readonly dialog = inject(MatDialog);

  selectedFile: File | null = null;
  uploading = false;
  uploadProgress = 0;
  errorMessage = '';
  documents: Document[] = [];
  constructor(
    private documentService: FileUploadService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.applicationId = this.route.snapshot.params['id'];
  }

  ngOnInit() {
    //
    // this.loadDocuments();
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.errorMessage = '';
  }

  onUpload() {
    if (!this.selectedFile) {
      this.errorMessage = 'Please select a file first';
      return;
    }

    this.uploading = true;
    this.uploadProgress = 0;
    this.errorMessage = '';

    this.documentService
      .uploadFile(this.selectedFile, this.applicationId)
      .subscribe({
        next: (document: any) => {
          this.uploading = false;
          this.uploadProgress = 100;
          this.selectedFile = null;
          this.fileUploaded.emit(document);
          this.openDialog();
          // this.loadDocuments();

          // Reset file input
          // const fileInput = document.getElementById(
          //   'fileInput'
          // ) as HTMLInputElement;
          // if (fileInput) {
          //   fileInput.value = '';
          // }
        },
        error: (error) => {
          this.uploading = false;
          this.uploadProgress = 0;
          this.errorMessage = error;
        },
      });
  }

  // loadDocuments() {
  //   this.documentService
  //     .getDocumentsByApplication(this.applicationId)
  //     .subscribe({
  //       next: (documents: any) => {
  //         this.documents = documents;
  //       },
  //       error: (error) => {
  //         this.errorMessage = error;
  //       },
  //     });
  // }

  // downloadDocument(document: any) {
  //   this.documentService.downloadFile(document.id).subscribe({
  //     next: (blob) => {
  //       const url = window.URL.createObjectURL(blob);
  //       const a = document.createElement('a');
  //       a.href = url;
  //       a.download = document.originalFileName;
  //       a.click();
  //       window.URL.revokeObjectURL(url);
  //     },
  //     error: (error) => {
  //       this.errorMessage = error;
  //     },
  //   });
  // }

  // deleteDocument(document: Document) {
  //   if (
  //     confirm(`Are you sure you want to delete ${document.originalFileName}?`)
  //   ) {
  //     this.documentService.deleteDocument(document.id).subscribe({
  //       next: () => {
  //         // this.loadDocuments();
  //       },
  //       error: (error) => {
  //         this.errorMessage = error;
  //       },
  //     });
  //   }
  // }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
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
