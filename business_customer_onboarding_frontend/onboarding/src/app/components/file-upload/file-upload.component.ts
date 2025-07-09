import { Component, Output, EventEmitter } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FileUploadService } from '../../core/services/file-upload.service';
import { Document } from '../../core/models/application.model';
import { MatIcon } from '@angular/material/icon';
import { MatSpinner } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
  imports: [MatIcon, MatSpinner, CommonModule],
})
export class FileUploadComponent {
  @Output() documentUploaded = new EventEmitter<Document>();

  isDragOver = false;
  isUploading = false;

  constructor(
    private fileUploadService: FileUploadService,
    private snackBar: MatSnackBar
  ) {}

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = true;
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = false;

    const files = event.dataTransfer?.files;
    if (files && files.length > 0) {
      this.uploadFile(files[0]);
    }
  }

  onFileSelect(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.uploadFile(file);
    }
  }

  private uploadFile(file: File): void {
    if (file.size > 10 * 1024 * 1024) {
      // 10MB limit
      this.snackBar.open('File size must be less than 10MB', 'Close', {
        duration: 5000,
        panelClass: ['error-snackbar'],
      });
      return;
    }

    const allowedTypes = [
      'application/txt',
      // 'application/pdf',
      // 'image/jpeg',
      // 'image/png',
      // 'image/jpg',
    ];
    // if (!allowedTypes.includes(file.type)) {
    //   this.snackBar.open('Only TXT files are allowed', 'Close', {
    //     duration: 5000,
    //     panelClass: ['error-snackbar'],
    //   });
    //   return;
    // }

    this.isUploading = true;

    this.fileUploadService.uploadFile(file, '1').subscribe({
      next: (response: any) => {
        const document: Document = {
          fileName: response.fileName,
          filePath: response.filePath,
          fileType: response.fileType,
          fileSize: parseInt(response.fileSize),
        };

        this.documentUploaded.emit(document);
        this.snackBar.open('File uploaded successfully!', 'Close', {
          duration: 3000,
          panelClass: ['success-snackbar'],
        });
        this.isUploading = false;
      },
      error: (error) => {
        this.snackBar.open(
          'Failed to upload file. Please try again.',
          'Close',
          {
            duration: 5000,
            panelClass: ['error-snackbar'],
          }
        );
        this.isUploading = false;
      },
    });
  }
}
