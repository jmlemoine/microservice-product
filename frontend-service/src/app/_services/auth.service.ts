import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Usuarios } from '../models/usuarios';

// const AUTH_API = 'http://localhost:8000/api/auth/';
const ZUUL_API = 'http://localhost:8765/api'
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  /*private userSubject: BehaviorSubject<Usuarios>;
  public usuario: Observable<Usuarios>;*/

  constructor(private http: HttpClient) {
    /*this.userSubject = new BehaviorSubject<Usuarios>(
      JSON.parse(localStorage.getItem('currentUsuarioLogueado')!)
    );
    this.usuario = this.userSubject.asObservable();*/
  }

  /*public get userValue(): Usuarios {
    return this.userSubject.value;
  }*/

  login(credentials: any): Observable<any> {
    //  return this.http.post(AUTH_API + 'signin', {
    return this.http.post(ZUUL_API + '/user/api/auth/signin', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
      /*.pipe(
        map(({ }) => {
          console.log("Klk con " + credentials.username);
          localStorage.setItem('currentUsuarioLogueado', JSON.stringify(currentUsuariocredentials.username));
          /*let currentUsuario: Usuarios = {
            username: credentials.username,
            password: credentials.password,
          };
          localStorage.setItem('currentUsuarioLogueado', JSON.stringify(/*currentUsuario*//*credentials.username));
          this.userSubject.next(currentUsuario);
          console.log("Klk con " + credentials.username);
          //return currentUsuario;
      })
    )*/
    
  }

  register(user: any): Observable<any> {
    return this.http.post(ZUUL_API + '/user/api/auth/signup', {
      username: user.username,
      email: user.email,
      //role: "",
      password: user.password,
      name: user.name,
      lastname: user.lastname,
      rol: "USER"//user.rol
    }, httpOptions);
  }

  registerAt(user: any): Observable<any> {
    return this.http.post(ZUUL_API + '/user/api/auth/signup', {
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
