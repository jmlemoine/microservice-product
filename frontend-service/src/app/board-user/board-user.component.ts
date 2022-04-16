import { Component, Input, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Usuarios } from '../models/usuarios'
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {

  content!: string;
  message = '';
  
  currentUser: any;
  usuarios?: Usuarios[];

  admin = false;
  user = false;

  usselected?: number;
  modifRol?: string;
  roles: any;
  

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private token: TokenStorageService
  ) { }

  @Input() viewMode = false;
  @Input() currentUsuario: Usuarios = {
    username: '',
    email: '',
    password: '',
    rols: '',
    name: '',
    lastname: '',
    role: ''
  }

  ngOnInit(): void {
    this.roles = [
      { id: 1, name: 'ADMIN' },
      { id: 2, name: 'USER' }
    ]
    this.usselected = 1;

    this.currentUser = this.token.getUser();
    this.userService.getUserBoard().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
    if (!this.viewMode) { 
      this.message = '';
      this.getUsuario(this.route.snapshot.params["id"]);
    }
  }

  onUserRol(val: any) {
    this.customFunction(val);
  }

  customFunction(val: any) {
    this.modifRol = val;
    this.currentUsuario.role = this.modifRol;
    console.log(this.currentUsuario.role);
  }

  sub() {
    if (this.user) {
      //this.selected = 'USER';
    }
    if (this.admin) {
      //this.selected = 'ADMIN';
    }
  }
  
  onSelectRole(value: Event) {
    console.log(value);
  }

  getUsuario(id: string): void {
    this.userService.get(id)
      .subscribe({
        next: (data) => {
          this.currentUsuario = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      })
  }

  deleteUsuario(): void {
    this.userService.delete(this.currentUsuario.id)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.toastr.success('El usuario ha sido eliminado exitosamente', 'Usuario Eliminado!')
          this.router.navigate(['/home']);
        }
      })
  }

  updateUsuario(): void {
    this.message = '';
    console.log(this.currentUsuario.role);
    this.userService.update(this.currentUsuario.id, this.currentUsuario)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message ? res.message : 'This tutorial was updated successfully!';
          this.toastr.success('El usuario ha sido modificado exitosamente', 'Usuario Modificado!')
          this.router.navigate(['/home']);
        },
        error: (e) => console.error(e)
      })
    /*this.tutorialService.update(this.currentTutorial.id, this.currentTutorial)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message ? res.message : 'This tutorial was updated successfully!';
        },
        error: (e) => console.error(e)
    });*/
  }

}
