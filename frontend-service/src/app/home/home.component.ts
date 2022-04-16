import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Planes } from '../models/planes';
import { Usuarios } from '../models/usuarios';
import { AuthService } from '../_services/auth.service';
import { PlanesService } from '../_services/planes.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import { IPayPalConfig, ICreateOrderRequest } from 'ngx-paypal';
import { environment } from './../../environments/environment';
import jwt_decode from 'jwt-decode';

//import * as email from 'nativescript-email';
//import { email } from "nativescript-email";

declare var paypal: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public payPalConfig?: IPayPalConfig;

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

/*  baseUrl = 'http://localhost:8001/api/planes';
  baseUrlUsers = 'http://localhost:8000/api/auth/usuarios';*/

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
  pb: number = 0;
  b: number = 0;
  ca: number = 0;
  ve: number = 0;

  //@ViewChild('paypal', { static: true }) paypalElement: ElementRef | undefined;

  producto = {
    descripcion: 'Producto en Venta',
    costo: /*this.totalPlanes*/500,
    img: 'Imagen de Producto'
  }

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

  messages?: string;// | undefined;
  displayUser?: string;// | undefined;
  userDisplayName?: string | null;


  calcularTotal() { 
    this.totalPlanes = 0;
    this.planes?.forEach((row: any) => {
      console.log("Nombre: "+row.nombre);
      console.log("Costo: "+row.costo);
      console.log(row.check);
      console.log(row.nombre.lastIndexOf('Cumpleaños'));//.lastIndexOf('Dodo'));
      if (row.check) { 
        this.totalPlanes += row.costo
      }
      console.log(this.totalPlanes);
    })
  }

  receiveUsuario($event: any) {
    //this.messages = $event;
    this.displayUser = $event;
  }

  @Input() usuarioLogged: Usuarios = {
    username: '',
    email: '',
    password: '',
    rols: '',
    role: ''
  }

  ngOnInit(): void {
    /*paypal
      .Buttons()
      .render(this.paypalElement?.nativeElement);*/
    //console.log("Nombre de Usuario: "+this.usuarioLogged.username);
    
    //this.userDisplayName = sessionStorage.getItem('currentUsuarioLogueado');
    this.userDisplayName = localStorage/*sessionStorage*/.getItem('currentUsuarioLogueado');
    //this.displayUser = jwt_decode(this.token)['username'];
    console.log("Username: "+this.userDisplayName);
    this.initConfig();
    
    this.currentUser = this.token.getUser();
    this.userService.getPublicContent().subscribe(
      data => {
        this.content = data;
        console.log(/*this.currentUser*/this.content);
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

  private initConfig(): void {
    
    this.payPalConfig = {
      currency: 'USD'/*'DOP'*//*'MXN'*/,
      clientId: environment.clientId,
      createOrderOnClient: (data) => <ICreateOrderRequest> {
        intent: 'CAPTURE',
        purchase_units: [{
          amount: {
            currency_code: 'USD'/*'DOP/*MXN'*/,
            value: '9.99',
            breakdown: {
              item_total: {
                currency_code: 'USD'/*'DOP'*//*'MXN'*/,
                value: '9.99'
              }
            }
          },
          items: [{
            name: 'Enterprise Subscription',
            quantity: '1',
            category: 'DIGITAL_GOODS',
            unit_amount: {
              currency_code: 'USD'/*'DOP'*//*'MXN'*/,
              value: '9.99',
            },
          }]
        }]
      },
      advanced: {
        commit: 'true'
      },
      style: {
        label: 'paypal',
        layout: 'vertical'
      },
      onApprove: (data, actions) => {
        console.log('onApprove - transaction was approved, but not authorized', data, actions);
        actions.order.get().then((details: any) => {
          console.log("Compra hecha por: " + this.userDisplayName);
          /*console.log("Pre-Boda: " + this.pb);
          console.log("Boda: " + this.b);
          console.log("Cumpleaños: " + this.ca);
          console.log("Vídeo de Evento: " + this.ve);*/
          this.calcularTotal();
          console.log("Total: "+this.totalPlanes);
          // Poner la función y la ruta con Zuul para registrar la compra, y enviar el correo inmediatamente
          console.log('onApprove - you can get full order details inside onApprove: ', details)
        });

      },
      onClientAuthorization: (data) => {
        console.log('onClientAuthorization - you should probably inform your server about completed transaction at this point', data);
        //this.showSuccess = true;
      },
      onCancel: (data, actions) => {
        console.log('OnCancel', data, actions);
        //this.showCancel = true;

      },
      onError: err => {
        console.log('OnError', err);
        //this.showError = true;
      },
      onClick: (data, actions) => {
        /*this.submit();
        console.log("Compra hecha por: " + this.userDisplayName);
        console.log('onClick', data, actions);*/
        //this.resetStatus();
      }
    };
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
    
    if (this.pre/* = true*/) {
      this.pb = 1000;
    }
    if (this.boda) {
      this.b = 5000;
    }
    if (this.hbd) {
      this.ca = 3000;
    }
    if (this.evento) {
      this.ve = 4000;
    }

    //total = pb + b + ca + ve;
    this.totalPlanes = this.pb + this.b + this.ca + this.ve;
    console.log("Pre-Boda: " + this.pb);
    console.log("Boda: " + this.b);
    console.log("Cumpleaños: " + this.ca);
    console.log("Vídeo de Evento: " + this.ve);
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

    console.log(pb + ":" + b + ":" + ca + ":"+ve);
    total = pb + b + ca + ve;
    console.log("Compra total: "+total);
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
          data.forEach((row: any)=>{row.check =false })
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
