import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Compras } from '../models/compras';
import { Planes } from '../models/planes';

//const baseUrl = 'http://localhost:8001/api/planes';
const ZUUL_API = 'http://localhost:8765/api';

@Injectable({
  providedIn: 'root'
})
export class PlanesService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Planes[]> {
    return this.http.get<Planes[]>(ZUUL_API + '/product/api/planes');
  }

  getAllCompras(): Observable<Compras[]> {
    return this.http.get<Planes[]>(ZUUL_API + '/product/api/planes');
  }

  create(data: any): Observable<any> {
    return this.http.post(ZUUL_API + '/product/api/compras', data);
  }

}
