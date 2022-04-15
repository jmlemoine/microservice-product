import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Usuarios } from '../models/usuarios';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  usuarios?: Usuarios[];

  messages = "Hey Usuario";
  displayUser = '';

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router) { }

  @Output() event = new EventEmitter<string>();

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.router.navigate(['/home']);
      //console.log("Username: " + this.form.username);
      //console.log(this.usuarios);
      //this.sendUsuario();
      
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  

  onSubmit(): void {
    /*console.warn(val);
    this.displayUser = val;
    this.event.emit(this.displayUser);*/
    this.authService.login(this.form).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.reloadPage();
        this.router.navigate(['/home']);
        localStorage/*sessionStorage*/.setItem('currentUsuarioLogueado', /*JSON.stringify(*/this.form.username/*)*/);//currentUsuario*//*credentials.username));
        console.log("Username: " + this.form.username);
        //this.sendUsuario(this.form.username);
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  handleResponse(data: any) {
    this.tokenStorage.saveToken(data.accessToken);
    sessionStorage.setItem('loggedUsuario', /*data.username*/this.form.username) //sessionStorage.setItem('loggedUser', data.Username);
    this.router.navigate(['/home']);
  }

  getVal(val: string) {
    console.warn(val);
    this.displayUser = val;
    this.event.emit(this.displayUser);
  }

  getValue(val: string) {
    console.warn(val);
    this.displayUser = val;
    //this.event.emit(this.displayUser);
  }

  sendUsuario(val: any) {
    //this.displayUser = this.form.username;
    val = this.usuarios;//this.form.username;
    this.event.emit(val/*this.displayUser*//*this.form.username*//*this.messages*//*this.form.username*/)
    //console.log("Usuario " + val/*this.displayUser*//*this.messages*//*this.form.username*/);
    console.log(this.usuarios);
  }

  reloadPage(): void {
    window.location.reload();
  }

}
