import { Component, Input, OnInit } from '@angular/core';
import { Usuarios } from '../models/usuarios';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit {

  currentUser: any;
  usuarios?: Usuarios[];
  textSpinner = '';

  @Input() currentUsuario: Usuarios = {
    username: '',
    email: '',
    password: '',
    rols: ''
  }

  constructor(
    private token: TokenStorageService,
    private userService: UserService,
    private spinner: NgxSpinnerService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.retrieveUsuarios();
  }

  retrieveUsuarios(): void {
    this.textSpinner = 'Cargando Usuarios ...'
    this.spinner.show();
    this.userService.getAllUsuarios()
      .subscribe({
        next: (data) => {
          this.usuarios = data;
          console.log(data);
          this.spinner.hide();
        },
        error: (e) => console.log(e)
      });
  }

  deleteUsuario(/*id: number*/): void {
    this.userService.delete(this.currentUsuario.id/*id*/)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.router.navigate(['/home']);
        }
      })
  }

  /*onDelete(id: number): void {
    //this.textSpinner = 'Eliminando Reserva ...'
    this.spinner.show();
    this.userService.delete(id).subscribe(data => {
      this.retrieveUsuarios();
    })
  }*/

}
