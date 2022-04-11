import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8000/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(credentials: any): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  register(user: any): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      username: user.username,
      email: user.email,
      //role: "",
      password: user.password,
      name: user.name,
      lastname: user.lastname,
      rol: user.rol
    }, httpOptions);
  }

  registerAt(user: any): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      username: "rose",
      email: "moxy04@gmail.com",
      //role: "",
      password: "kokykoky",
      name: "Roselin",
      lastname: "Sosa",
      rol: "ADMIN"
    }, httpOptions);
  }

}
