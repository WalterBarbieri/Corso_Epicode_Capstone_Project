import { Component,  OnInit } from '@angular/core';
import { AuthData } from 'src/app/auth/auth.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { Utente } from 'src/app/models/utente.interface';
import { UserService } from 'src/app/service/user.service';
import { Subscription } from 'rxjs';


@Component({
    selector: 'app-userpage',
    templateUrl: './userpage.component.html',
    styleUrls: ['./userpage.component.scss'],
})
export class UserpageComponent implements OnInit {
    user!: AuthData | null;
    utente!: Utente | null;
    sub!: Subscription;


    constructor(
        private authService: AuthService,
        private userService: UserService,
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




}


