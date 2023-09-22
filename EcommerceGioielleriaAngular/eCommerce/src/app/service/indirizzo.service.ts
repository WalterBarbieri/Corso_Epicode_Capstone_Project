import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Comune } from '../models/comune.interface';
import { Observable } from 'rxjs';
import { Indirizzo } from '../models/indirizzo.interface';
import { IndirizzoPayload } from '../models/indirizzo-payload.interface';

@Injectable({
  providedIn: 'root'
})
export class IndirizzoService {

    baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  eliminaIndirizzo(id: string) {
    return this.http.delete(`${this.baseUrl}indirizzi/${id}`)
  }

  getComuni() {
    return this.http.get<Comune[]>(`${this.baseUrl}comuni`)
  }

  findComuneByDenominazione(denominazione: string, nomeProvincia: string | null, nomeRegione: string | null ): Observable<Comune[]> {

    let params = new HttpParams();

    params = params.set('denominazione', denominazione);
    if (nomeProvincia) {
        params = params.set('nomeProvincia', nomeProvincia);
    }

    if (nomeRegione) {
        params = params.set('nomeRegione', nomeRegione)
    }

    return this.http.get<Comune[]>(`${this.baseUrl}comuni`, {params})
  }

  aggiungiIndirizzo(indirizzo: IndirizzoPayload){
    return this.http.post<IndirizzoPayload>(`${this.baseUrl}indirizzi/indirizzi`, indirizzo);
  }
}
