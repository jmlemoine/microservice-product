import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Planes } from '../models/planes';
const baseUrl = 'http://localhost:8001/api/planes';

@Injectable({
  providedIn: 'root'
})
export class PlanesService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Planes[]> {
    return this.http.get<Planes[]>(baseUrl);
  }

}
