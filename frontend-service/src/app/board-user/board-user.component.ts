import { Component, Input, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Usuarios } from '../models/usuarios'
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {

  content!: string;
  message = '';
  
  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) { }

  @Input() viewMode = false;
  @Input() currentUsuario: Usuarios = {
    username: '',
    email: '',
    password: '',
    rols: ''
  }

  ngOnInit(): void {
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
          this.router.navigate(['/home']);
        }
      })
  }

  updateUsuario(): void {
    this.message = '';
    this.userService.update(this.currentUsuario.id, this.currentUsuario)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message ? res.message : 'This tutorial was updated successfully!';
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
