import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Utente } from 'src/app/models/utente.interface';
import { UserService } from 'src/app/service/user.service';


@Component({
  selector: 'app-gestione-utenti',
  templateUrl: './gestione-utenti.component.html',
  styleUrls: ['./gestione-utenti.component.scss']
})
export class GestioneUtentiComponent implements OnInit {
    utenti: Utente[] | null = [];
    utenteToasts: { [key: string]: boolean } = {};
    pages: number[] = [];
    paginaSelezionata: number = 1;
    ricercaUtente = '';
    nome = '';
    cognome = '';


  constructor(private userService: UserService) {

   }

  ngOnInit(): void {

    this.utenteToasts = {};

    if (this.utenti) {
        this.utenti.forEach((utente) => {
            if(utente.id){
                this.utenteToasts[utente.id] = false;
            }

        });
    }
  }

  eliminaUtente(email: string){
    this.userService.eliminaUtente(email).subscribe(
        () => {
            console.log('Utente eliminato con successo');
            this.caricaUtenti();
        },
        (error) => {
            if (error instanceof HttpErrorResponse && error.status === 200) {

                console.log('Utente eliminato con successo');

                this.caricaUtenti();

            } else {

                console.error('Errore durante l\'eliminazione dell\'utente', error);
            }
        }
    );

  }

  caricaUtenti() {
    this.userService.recuperaUtenti(this.paginaSelezionata - 1, this.nome, this.cognome).subscribe(
        (response: any) => {
            this.utenti = response.content;
            this.pages = Array.from({length: response.totalPages}, (_, i) => i + 1)
            console.log('Lista utenti aggiornata', this.utenti);
        },
        (error) => {
            console.error('Errore durante il recupero degli utenti', error);
        }
    );
}
effettuaRicerca(){
    const splitRicerca = this.ricercaUtente.split(' ');
    this.nome = splitRicerca[0] || '';
    this.cognome = splitRicerca[1] || '';
    this.caricaUtenti();
}


  openToast(id: string) {
    this.utenteToasts[id] = true;
  }
  closeToast(id: string) {
    this.utenteToasts[id] = false;
  }

  selectPage(page: number) {
    this.paginaSelezionata = page;

    this.caricaUtenti();

  }

}
