import { inject } from '@angular/core';
import {
  HttpInterceptorFn,
  HttpRequest,
  HttpHandlerFn,
  HttpErrorResponse,
} from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const errorHandlerInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
) => {
  const snackBar = inject(MatSnackBar);
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMsg = 'An unexpected error occurred';

      if (error.error instanceof ErrorEvent) {
        errorMsg = `Client-side error: ${error.error.message}`;
      } else {
        switch (error.status) {
          case 0:
            errorMsg = 'Server unreachable. Check your network connection.';
            break;
          case 400:
            // errorMsg = 'Bad request.';
            errorMsg = `Client-side error: ${error.error.message}`;
            break;
          case 401:
            errorMsg = 'Unauthorized. Redirecting to login.';
            router.navigate(['/login']);
            break;
          case 403:
            errorMsg = 'Access denied.';
            break;
          case 404:
            errorMsg = 'Resource not found.';
            break;
          case 500:
            errorMsg = 'Internal server error.';
            break;
          default:
            errorMsg = `Error ${error.status}: ${error.message}`;
        }
      }

      snackBar.open(errorMsg, 'Close', {
        duration: 10000,
        verticalPosition: 'bottom',
        panelClass: ['error-snackbar'],
      });

      return throwError(() => error);
    })
  );
};
