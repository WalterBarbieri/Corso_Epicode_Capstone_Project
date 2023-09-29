import { Component,  ElementRef,  OnInit } from '@angular/core';
import { AuthData } from 'src/app/auth/auth.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { Utente } from 'src/app/models/utente.interface';
import { UserService } from 'src/app/service/user.service';
import { Subscription } from 'rxjs';
import { IndirizzoService } from 'src/app/service/indirizzo.service';
import { ModalService } from 'src/app/service/modal.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UtentePayload } from 'src/app/models/utente-payload.interface';


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
    editMode = false;
    utenteForm!: FormGroup;
    indirizzoToasts: { [key: string]: boolean } = {};


    private modalCloseSubscription!: Subscription;
    private addedIndirizzoSubscription!: Subscription;


    constructor(
        private authService: AuthService,
        private userService: UserService,
        private indirizzoService: IndirizzoService,
        private modalService: ModalService,
        private formBuilder: FormBuilder
       ) {}

    ngOnInit(): void {

        this.authService.user$.subscribe(_user => {
            this.user = _user;
            if (this.user) {
                this.sub = this.userService.recuperaUtenteById(this.user?.utenteTokenResponse.id).subscribe((currentUtente: Utente) => {
                    this.utente = currentUtente;

                    console.log(this.utente);
                })


            }
        });

        this.utenteForm = this.formBuilder.group({
            nome: [''],
            cognome: [''],
            dataNascita: [''],
            email: ['', Validators.email]
        });

        this.utenteForm.disable();



        this.modalCloseSubscription = this.modalService.closeModal$.subscribe(() => {
            this.toggleModal();
        })

        this.addedIndirizzoSubscription = this.modalService.indirizzoAggiunto$.subscribe(() => {

            this.authService.user$.subscribe(_user => {
                this.user = _user;
                if (this.user) {
                    this.sub = this.userService.recuperaUtenteById(this.user?.utenteTokenResponse.id).subscribe((currentUtente: Utente) => {
                        this.utente = currentUtente;

                        console.log(this.utente);
                    })


                }
            })
        })

        this.indirizzoToasts = {};

        if (this.utente && this.utente.indirizzi) {
            this.utente.indirizzi.forEach((indirizzo) => {
            this.indirizzoToasts[indirizzo.id] = false;
            });
        }


    }

    ngOnDestroy(): void {
        this.modalCloseSubscription.unsubscribe();
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

    attivaModifiche() {
        this.utenteForm.enable();
    }

    annullaModifiche(){
        this.utenteForm.disable();
        this.utenteForm.patchValue( {
            nome: this.utente?.nome,
            cognome: this.utente?.cognome,
            dataNascita: this.utente?.dataNascita,
            email: this.utente?.email
        })
    }

    modificaUtente(){
        if (this.utenteForm.valid){
            const utenteModificato: UtentePayload = {};

            if(this.utenteForm.get('nome')?.value != '' && this.utenteForm.get('nome')?.value != this.utente?.nome){
                utenteModificato.nome = this.utenteForm.get('nome')?.value;
            }
            if(this.utenteForm.get('cognome')?.value != '' && this.utenteForm.get('cognome')?.value != this.utente?.cognome){
                utenteModificato.cognome = this.utenteForm.get('cognome')?.value;
            }
            if(this.utenteForm.get('dataNascita')?.value != '' && this.utenteForm.get('dataNascita')?.value != this.utente?.dataNascita){
                utenteModificato.dataNascita = this.utenteForm.get('dataNascita')?.value;
            }
            if(this.utenteForm.get('email')?.value != '' && this.utenteForm.get('email')?.value != this.utente?.email){
                utenteModificato.email = this.utenteForm.get('email')?.value;
            }
            console.log(utenteModificato);


            this.userService.modificaUtente(this.utente!.email, utenteModificato).subscribe((response) => {
                console.log("Utente modificato con successo", response);
                if(this.utente && response.nome != undefined && response.cognome != undefined && response.dataNascita != undefined && response.email != undefined){
                    this.utente.nome = response.nome;
                    this.utente.cognome = response.cognome;
                    this.utente.dataNascita = response.dataNascita;
                    this.utente.email = response.email;
                }

                const infoStorage = localStorage.getItem('user');
                if (infoStorage){
                    const myUserStorage = JSON.parse(infoStorage);
                        myUserStorage.utenteTokenResponse.nome = this.utente!.nome;
                        myUserStorage.utenteTokenResponse.cognome = this.utente!.cognome;
                        myUserStorage.utenteTokenResponse.email = this.utente!.email;
                        localStorage.setItem('user', JSON.stringify(myUserStorage));

                }


                this.utenteForm.disable();

            }, (error) => {
                console.error('Errore durante la modifica dell\'utente', error);

            })
        }
    }

    openToast(id: string) {
        this.indirizzoToasts[id] = true;
      }
      closeToast(id: string) {
        this.indirizzoToasts[id] = false;
      }




}


