import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Planes } from '../models/planes';
import { Usuarios } from '../models/usuarios';
import { AuthService } from '../_services/auth.service';
import { PlanesService } from '../_services/planes.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

//import * as email from 'nativescript-email';
//import { email } from "nativescript-email";

const AUTH_API = 'http://localhost:8000/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  content!: string;
  currentUser: any;

  form: any = {};
  forms: any = {};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  planes?: Planes[];
  usuarios?: Usuarios[];
  currentPlan: Planes = {};
  currentUsuario: Usuarios = {};
  currentIndex = -1;
  nombre = '';

  baseUrl = 'http://localhost:8001/api/planes';
  baseUrlUsers = 'http://localhost:8000/api/auth/usuarios';

  saveCheck: boolean = false;

  labelPosition: 'before' | 'after' = 'after';
  checked = false;
  indeterminate = false;
  disabled = false;

  pre = false;
  boda = false;
  hbd = false;
  evento = false;

  totalPlanes = 0;


  constructor(private userService: UserService,
    private authService: AuthService,
    private token: TokenStorageService,
    private planesService: PlanesService,
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private fb: FormBuilder
    ) {
      this.form = formBuilder.group({
        products: formBuilder.array([], [Validators.required]),
      })
    }

    

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.userService.getPublicContent().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
    console.log("Klk Homee");
    this.register();
    this.retrievePlanes();
    this.retrieveUsuarios();
    this.forms = this.fb.group({
      checkArray: this.fb.array([], [Validators.required])
    })
    //this.onSubmit();
  }

  onCheckboxChange(name: any, isChecked: boolean) {
      
    const cartoons = (this.form.controls.name as FormArray);

    if (isChecked) {
      cartoons.push(new FormControl(name));
    } else {
      const index = cartoons.controls.findIndex(x => x.value === name);
      cartoons.removeAt(index);
    }

  }

  /*onCheckboxChange(e: any) {
    const products: FormArray = this.form.get('products') as FormArray;
   
    if (e.target.checked) {
      products.push(new FormControl(e.target.value));
    } else {
       const index = products.controls.findIndex(x => x.value === e.target.value);
       products.removeAt(index);
    }
  }*/

  sub() { 
    let pb: number = 0;
    let b: number = 0;
    let ca: number = 0;
    let ve: number = 0;
    
    let total: number = 0;

    if (this.pre/* = true*/) {
      pb = 1000;
    }
    if (this.boda) {
      b = 5000;
    }
    if (this.hbd) {
      ca = 3000;
    }
    if (this.evento) {
      ve = 4000;
    }

    //total = pb + b + ca + ve;
    this.totalPlanes = pb + b + ca + ve;
    console.log(this.totalPlanes);

  }

  submit() {

    let pb: number = 0;
    let b: number = 0;
    let ca: number = 0;
    let ve: number = 0;
    
    let total: number = 0;

    //console.log(this.pre/* = true*/);
    if (this.pre/* = true*/) {
      pb = 1000;
    }
    if (this.boda) {
      b = 5000;
    }
    if (this.hbd) {
      ca = 3000;
    }
    if (this.evento) {
      ve = 4000;
    }

    total = pb + b + ca + ve;
    console.log(total);
  }
    
    

  /*public saveCheckChanged(pre:any, boda: boolean){
    
    let pb: number = 0;
    let b: number = 0;
    let ca: number = 0;
    let ve: number = 0;

    this.pre = pre;
    this.boda = boda;
    
    console.log(pre, boda);
  }*/

  /*onSubmit(f: NgForm) {
    Email.send({
      Host : ‘smtp.elasticemail.com’,
      Username : ‘udith.indrakantha@gmail.com’, 
      Password : ‘**************************’,          //Enter your password here
      To : ‘udith.indrakantha@gmail.com’,
      From : `udith.indrakantha@gmail.com`,
      Subject : this.model.subject,
      Body : `
            <i>This is sent as a feedback from my resume page.</i> <br/>
            <b>Name: 
            </b>${this.model.name} <br />
            <b>Email: </b>${this.model.email}<br />
            <b>Subject: 
            </b>${this.model.subject}<br />
            <b>Message:</b> <br /> 
            ${this.model.message} <br> 
            <br> <b>~End of Message.~</b> `
    }).then( 
    message => {alert(message); f.resetForm(); } 
    );
    }
  }*/

  retrievePlanes(): void {
    this.planesService.getAll()
      .subscribe({
        next: (data) => {
          this.planes = data;
          console.log(data);
        },
        error: (e) => console.log(e)
      });
  }

  retrieveUsuarios(): void {
    this.userService.getAllUsuarios()
      .subscribe({
        next: (data) => {
          this.usuarios = data;
          console.log(data);
        },
        error: (e) => console.log(e)
      });
  }

  register(): void {
    this.authService.registerAt(this.form).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

  /*sendEmail() {
    email.available().then
  }*/

  /*sendEmail() {
    email.available().then(available => {
      console.log(`The device email status is ${available}`);
    }).catch(error => console.error(error));
  }*/

}
