import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuarios } from '../models/usuarios';

const API_URL = 'http://localhost:8000/api/test/';
//const AUTH_API = 'http://localhost:8000/api/auth/usuarios';
const ZUUL_API = 'http://localhost:8765/api/user/api/auth/usuarios';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsuarios(): Observable<Usuarios[]> {
    return this.http.get<Usuarios[]>(ZUUL_API);
  }

  get(id: any): Observable<Usuarios> {
    return this.http.get(`${ZUUL_API}/${id}`);
    //return this.http.get<Usuarios[]>(AUTH_API+id);
  }


  // Verificar la ruta de Zuul de update
  update(id: any, data: any): Observable<any> {
    return this.http.put(`${ZUUL_API}/${id}`, data);
  }

  // Verificar la ruta de Zuul de delete
  delete(id: any): Observable<any> {
    return this.http.delete(`${ZUUL_API}/${id}`);
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
