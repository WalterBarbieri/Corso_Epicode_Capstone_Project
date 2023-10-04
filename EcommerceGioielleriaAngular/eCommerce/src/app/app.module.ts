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
import { OrdineComponent } from './components/ordine/ordine.component';
import { PaypalComponent } from './components/paypal/paypal.component';
import { OrdineSuccessoComponent } from './components/ordine/ordine-successo/ordine-successo.component';
import { OrdineAnnullatoComponent } from './components/ordine/ordine-annullato/ordine-annullato.component';
import { AuthGuard } from './auth/auth.guard';

const route: Route[] = [
    {
        path: '',
        component: HomeComponent,
    },

    {
        path: 'register',
        component: RegisterComponent,
    },

    {
        path: 'login',
        component: LoginComponent,
    },

    {
        path: 'login/:message',
        component: LoginComponent,
    },

    {
        path: 'contatti',
        component: ContattiComponent,
    },
    {
        path: 'userpage',
        component: UserpageComponent,
        canActivate: [AuthGuard],
        data: {
            roles: ['USER', 'ADMIN']
        }
    },
    {
        path: 'prodotti',
        component: ProdottiComponent,
    },
    {
        path: 'prodotti/:categoria',
        component: ProdottiComponent,
    },
    {
        path: 'adminpage',
        component: AdminpageComponent,
        canActivate: [AuthGuard],
        data: {
            roles: ['ADMIN']
        }
    },
    {
        path: 'history',
        component: HistoryComponent,
    },
    {
        path: 'dettaglio/:id',
        component: DettaglioComponent,
    },
    {
        path: 'carrello',
        component: CarrelloComponent,
        canActivate: [AuthGuard],
        data: {
            roles: ['USER', 'ADMIN']
        }
    },
    {
        path: 'ordine/:id',
        component: OrdineComponent,
        canActivate: [AuthGuard],
        data: {
            roles: ['USER', 'ADMIN']
        }
    },
    {
        path: 'paypal/:ordineId',
        component: PaypalComponent,
        canActivate: [AuthGuard],
        data: {
            roles: ['USER', 'ADMIN']
        }
    },
    {
        path: 'ordineSuccesso',
        component: OrdineSuccessoComponent,
        canActivate: [AuthGuard],
        data: {
            roles: ['USER', 'ADMIN']
        }
    },
    {
        path: 'ordineAnnullato',
        component: OrdineAnnullatoComponent,
        canActivate: [AuthGuard],
        data: {
            roles: ['USER', 'ADMIN']
        }
    },
];
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
        CarrelloComponent,
        OrdineComponent,
        PaypalComponent,
        OrdineSuccessoComponent,
        OrdineAnnullatoComponent,
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        RouterModule.forRoot(route),
        ReactiveFormsModule,
        NgbModule,
    ],
    providers: [
        AuthService,
        AuthGuard,
        { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
