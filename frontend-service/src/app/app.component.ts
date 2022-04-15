import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from './_services/token-storage.service';
import { render } from 'creditcardpayments/creditCardPayments';

declare var paypal: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend-service';
  private roles!: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username!: string;

  @ViewChild('paypal', { static: true }) paypalElement: ElementRef | undefined;

  producto = {
    descripcion: 'Producto en Venta',
    costo: /*this.totalPlanes*/500,
    img: 'Imagen de Producto'
  }

  constructor(private tokenStorageService: TokenStorageService, private http: HttpClient) {

    /*render(
      {
        id: "myPaypalButtons",
        currency: "USD",
        value: "10.00",
        onApprove: (details: any) => {
          alert("Transaction Successfull!");
        }
      }
    )*/

  }
  
  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      console.log("klk "+user.username);
      this.roles = user.roles;
      if (typeof(this.roles) !== undefined) {
        console.log("NOT NULO");
        
      }
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');
      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

}
