import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();

    if (token && req.headers.has('Use-Auth')) {
      const clonedReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        },
        headers: req.headers.delete('Use-Auth')
      });
      
      return next.handle(clonedReq);
    }

    return next.handle(req);
  }
}
