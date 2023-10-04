import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Gioiello } from '../models/gioiello.interface';

import { catchError, map } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class GioielloService {
    baseUrl = environment.baseURL;



  constructor(private http: HttpClient) {
}

  recuperaProdotti(page:number, sort: string){
    return this.http.get<Gioiello[]>(`${this.baseUrl}gioielli?page=${page}&sort=${sort}`)
  }
  recuperaProdottyByCategoria(categoria: string, page:number, sort: string) {
    return this.http.get<Gioiello[]>(`${this.baseUrl}gioielli?categoria=${categoria}&page=${page}&sort=${sort}`)
  }

  recuperaProdotto(id: string) {
    return this.http.get<Gioiello>(`http://localhost:3001/gioielli/${id}`).pipe(
      map((response: any) => {
        return response as Gioiello;
      }),
      catchError((error: any) => {
        console.error('Errore nella richiesta:', error);
        throw error;
      })
    );
  }



  creaProdotto(formData: FormData){


    return this.http.post(`${this.baseUrl}gioielli`, formData);
  }

  eliminaProdotto(id: string){
    return this.http.delete(`http://localhost:3001/gioielli/${id}`)
  }
}
