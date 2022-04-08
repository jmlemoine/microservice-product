import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuarios } from '../models/usuarios';

const API_URL = 'http://localhost:8000/api/test/';
const AUTH_API = 'http://localhost:8000/api/auth/usuarios';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsuarios(): Observable<Usuarios[]> {
    return this.http.get<Usuarios[]>(AUTH_API);
  }

  get(id: any): Observable<Usuarios> {
    return this.http.get(`${AUTH_API}/${id}`);
    //return this.http.get<Usuarios[]>(AUTH_API+id);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${AUTH_API}/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${AUTH_API}/${id}`);
  }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }

}
