import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Utente } from '../models/utente.interface';
import { environment } from 'src/environments/environment';
import { UtentePayload } from '../models/utente-payload.interface';

@Injectable({
    providedIn: 'root',
})
export class UserService {
    baseUrl = environment.baseURL;

    constructor(private http: HttpClient) {}

    recuperaUtenti(page:number, nome: string, cognome: string) {
        return this.http.get<Utente[]>(`${this.baseUrl}utenti/cerca?page=${page}&nome=${nome}&cognome=${cognome}`);
    }

    recuperaUtenteByEmail(email: string) {
        return this.http.get<Utente>(`${this.baseUrl}utenti?email=${email}`);
    }

    recuperaUtenteById(id: string) {
        return this.http.get<Utente>(`${this.baseUrl}utenti?id=${id}`);
    }

    modificaUtente(email: string, utenteModificato: UtentePayload) {
        return this.http.patch<UtentePayload>(
            `${this.baseUrl}utenti/${email}`,
            utenteModificato
        );
    }

    eliminaUtente(email: string){
        return this.http.delete(`${this.baseUrl}utenti/${email}`)
    }

}
