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
  /**  metodo per richiamare recupera prodotti, spostato da home.ts era là per prova
  users!: Utente[];
    sub!: Subscription;

  this.sub = this.recuperaProdotti().subscribe((testUsers: any[]) => {

    this.users = testUsers;
    console.log(this.users);
})
 */

    recuperaUtenteByEmail(email: string){
        return this.http.get<Utente>(`${this.baseUrl}utenti?email=${email}`)
    }
}
