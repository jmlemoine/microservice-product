import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
// services

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor () { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with jwt token if available
    request = request.clone({
      setHeaders: {
        'Access-Control-Allow-Origin': `*`
      }
    });

    return next.handle(request);
  }
}
