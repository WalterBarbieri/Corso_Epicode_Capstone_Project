import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { OrdinePayload } from '../models/ordine-payload.interface';
import { Ordine } from '../models/ordine.interface';

@Injectable({
  providedIn: 'root'
})
export class OrdineService {
    baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

    creaOrdine(ordinePayload: OrdinePayload, importo: number, iva: number, totale: number){
        return this.http.post<Ordine>(`${this.baseUrl}ordini?importo=${importo}&iva=${iva}&totale=${totale}`, ordinePayload);
    }

    recuperaOrdineByOrdineId(ordineId: string){
        return this.http.get<Ordine>(`${this.baseUrl}ordini/${ordineId}`);
    }

    recuperaOrdineByUtenteId(id: string) {
        return this.http.get<Ordine>(`${this.baseUrl}ordini/utente?id=${id}`);
    }

    modificaStatoOrdine(ordineId: string, statoOrdine: string) {
        const url = `${this.baseUrl}ordini/${ordineId}?`;
        const params = {
            statoOrdine: statoOrdine
        }
        return this.http.patch<Ordine>(url, null, {params})
    }

    eliminaOrdine(ordineId: string){
        return this.http.delete(`${this.baseUrl}ordini/${ordineId}`);
    }
}
