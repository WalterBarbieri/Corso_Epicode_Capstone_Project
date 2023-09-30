import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { OrderItem } from '../models/order-item.interface';
import { GioielloService } from './gioiello.service';

@Injectable({
  providedIn: 'root'
})
export class OrderItemService {
    baseUrl = environment.baseURL;
  constructor(private http: HttpClient, private gioielloService: GioielloService) { }

  recuperaOrderItemByUtenteId(id: String){
    return this.http.get<any>(`${this.baseUrl}order-item?id=${id}`)
  }

  recuperaOrderItemById(orderItemId: string){
    return this.http.get<OrderItem>(`${this.baseUrl}order-item/${orderItemId}`)
  }
  getProdottoById(prodottoId: string) {
    return this.gioielloService.recuperaProdotto(prodottoId);
  }


  creaOrderItem(id: string) {
    const params = new HttpParams().set('id', id);
    return this.http.post<OrderItem>(`${this.baseUrl}order-item`, {}, { params });
  }

  addProduct(orderItemId: string, productId: string, quantita: number) {
    const url = `${this.baseUrl}order-item/${orderItemId}/add-product`;
    const params = {
      id: productId,
      quantita: quantita.toString()
    };

    return this.http.post(url, null, {params});
  }

  increaseProductQuantity(orderItemId: string, productId: string){
    const url = `${this.baseUrl}order-item/${orderItemId}/plus?`;
    const params = {
        id: productId
    }

    return this.http.post(url, null, { params });

  }

  decreaseProductQuantity(orderItemId: string, productId: string){
    const url = `${this.baseUrl}order-item/${orderItemId}/minus?`;
    const params = {
        id: productId
    }

    return this.http.post(url, null, { params });

  }

  removeProduct(orderItemId: string, productId: string){
    return this.http.delete(`${this.baseUrl}order-item/${orderItemId}/remove-product?id=${productId}`)
  }

  removeOrderItem(orderItemId: string){
    return this.http.delete(`${this.baseUrl}order-item/${orderItemId}`)
  }


}
