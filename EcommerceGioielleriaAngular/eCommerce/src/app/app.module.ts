import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Route, RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { RegisterComponent } from './auth/register/register/register.component';
import { LoginComponent } from './auth/login/login/login.component';
import { AuthService } from './auth/auth.service';
import { TokenInterceptor } from './auth/token.interceptor';
import { ContattiComponent } from './components/contatti/contatti.component';
import { TextareaAutoresizeDirective } from './textarea-autoresize.directive';
import { UserpageComponent } from './components/userpage/userpage.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ModalindirizzoComponent } from './components/modalindirizzo/modalindirizzo.component';



const route: Route[] = [
    {
        path: '',
        component: HomeComponent
    },

    {
        path: 'register',
        component: RegisterComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'contatti',
        component: ContattiComponent
    },
    {
        path: 'userpage',
        component: UserpageComponent
    }
]
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    FooterComponent,
    RegisterComponent,
    LoginComponent,
    ContattiComponent,
    TextareaAutoresizeDirective,
    UserpageComponent,
    ModalindirizzoComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(route),
    ReactiveFormsModule
  ],
  providers: [AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
