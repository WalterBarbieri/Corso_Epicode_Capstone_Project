import { Component,  OnInit } from '@angular/core';
import { AuthData } from 'src/app/auth/auth.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { Utente } from 'src/app/models/utente.interface';
import { UserService } from 'src/app/service/user.service';
import { Subscription } from 'rxjs';
import { IndirizzoService } from 'src/app/service/indirizzo.service';
import { error } from 'jquery';


@Component({
    selector: 'app-userpage',
    templateUrl: './userpage.component.html',
    styleUrls: ['./userpage.component.scss'],
})
export class UserpageComponent implements OnInit {
    user!: AuthData | null;
    utente!: Utente | null;
    sub!: Subscription;
    isModalOpen = false;


    constructor(
        private authService: AuthService,
        private userService: UserService,
        private indirizzoService: IndirizzoService
       ) {}

    ngOnInit(): void {

        this.authService.user$.subscribe(_user => {
            this.user = _user;
            if (this.user) {
                this.sub = this.userService.recuperaUtenteByEmail(this.user?.utenteTokenResponse.email).subscribe((currentUtente: Utente) => {
                    this.utente = currentUtente;

                    console.log(this.utente);
                })


            }
        })


    }

    toggleModal(){
        this.isModalOpen = !this.isModalOpen;
    }

    eliminaIndirizzo(id:string) {
        this.sub = this.indirizzoService.eliminaIndirizzo(id).subscribe(() => {
            this.utente?.indirizzi?.splice(this.utente?.indirizzi?.findIndex((indirizzo) => indirizzo.id === id), 1);
        }, (error) => {
            console.error(error.error);

        })
    }




}


