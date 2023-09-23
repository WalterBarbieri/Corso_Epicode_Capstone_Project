import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Gioiello } from '../models/gioiello.interface';

@Injectable({
  providedIn: 'root'
})
export class GioielloService {
    baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  recuperaProdotti(){
    return this.http.get<Gioiello[]>(`${this.baseUrl}gioielli`)
  }
  recuperaProdottyByCategoria(categoria: string) {
    return this.http.get<Gioiello[]>(`${this.baseUrl}gioielli?categoria=${categoria}`)
  }
}
