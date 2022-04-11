import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { NgxPayPalModule } from 'ngx-paypal';
import { UsuariosComponent } from './usuarios/usuarios.component'
import { ToastrModule } from 'ngx-toastr';
import { ErrorStateMatcher, ShowOnDirtyErrorStateMatcher } from '@angular/material/core';

/*import { platformNativeScriptDynamic, NativeScriptModule } from "nativescript-angular/platform";
import { NativeScriptHttpModule } from "nativescript-angular/http";
import { NativeScriptFormsModule } from "nativescript-angular/forms";*/

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RegisterComponent,
    LoginComponent,
    ProfileComponent,
    BoardUserComponent,
    BoardModeratorComponent,
    BoardAdminComponent,
    UsuariosComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatCheckboxModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    NgxPayPalModule,
    ToastrModule.forRoot(),
    /*,
    NativeScriptModule,
    NativeScriptFormsModule,
    NativeScriptHttpModule*/
  ],
  providers: [
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
