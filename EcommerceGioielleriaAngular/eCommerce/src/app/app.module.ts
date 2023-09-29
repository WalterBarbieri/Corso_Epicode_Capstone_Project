import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Route, RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


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
import { ModalindirizzoComponent } from './components/modalindirizzo/modalindirizzo.component';
import { ProdottiComponent } from './components/prodotti/prodotti.component';
import { AdminpageComponent } from './components/adminpage/adminpage.component';
import { HistoryComponent } from './components/history/history.component';
import { DettaglioComponent } from './components/dettaglio/dettaglio.component';
import { GestioneProdottiComponent } from './components/adminpage/gestione-prodotti/gestione-prodotti.component';
import { GestioneUtentiComponent } from './components/adminpage/gestione-utenti/gestione-utenti.component';
import { CarrelloComponent } from './components/carrello/carrello.component';



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
        path: 'login/:message',
        component: LoginComponent
    },

    {
        path: 'contatti',
        component: ContattiComponent
    },
    {
        path: 'userpage',
        component: UserpageComponent
    },
    {
        path: 'prodotti',
        component: ProdottiComponent
    },
    {
        path: 'prodotti/:categoria',
        component: ProdottiComponent
    },
    {
        path: 'adminpage',
        component: AdminpageComponent
    },
    {
        path: 'history',
        component: HistoryComponent
    },
    {
        path: 'dettaglio/:id',
        component: DettaglioComponent
    },
    {
        path: 'carrello',
        component: CarrelloComponent
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
    ModalindirizzoComponent,
    ProdottiComponent,
    AdminpageComponent,
    HistoryComponent,
    DettaglioComponent,
    GestioneProdottiComponent,
    GestioneUtentiComponent,
    CarrelloComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(route),
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
