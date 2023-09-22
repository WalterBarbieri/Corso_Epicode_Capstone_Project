import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Utente } from '../models/utente.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
    baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  recuperaUtenti(){

    return this.http.get<Utente[]>(`${this.baseUrl}utenti`)
  }

    recuperaUtenteByEmail(email: string){
        return this.http.get<Utente>(`${this.baseUrl}utenti?email=${email}`)
    }
}
