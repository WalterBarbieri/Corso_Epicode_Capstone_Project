import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { catchError } from 'rxjs';
import { AuthData } from 'src/app/auth/auth.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { Gioiello } from 'src/app/models/gioiello.interface';
import { Immagine } from 'src/app/models/immagine.interface';
import { GioielloService } from 'src/app/service/gioiello.service';
import { OrderItemService } from 'src/app/service/order-item.service';

@Component({
  selector: 'app-dettaglio',
  templateUrl: './dettaglio.component.html',
  styleUrls: ['./dettaglio.component.scss']
})
export class DettaglioComponent implements OnInit {

    gioiello!: Gioiello;
    immagini: Immagine[] = [];
    quantita: number[] = [];
    quantitaSelezionata: number = 1;
    user!: AuthData | null;
    showToast = false;
    toastMessage: string = '';
    showCartToast = false;


  constructor(private route: ActivatedRoute, private gioielloService: GioielloService, private authService: AuthService, private orderItemService: OrderItemService) { }

  ngOnInit(): void {

    this.route.paramMap.subscribe((params) => {
        const id = params.get('id');

        if(id){
            this.gioielloService.recuperaProdotto(id).subscribe((response) => {
                this.gioiello = response;
                this.immagini = response.immagini
                this.quantita = Array.from({length: response.quantita}, (_, i) => i + 1)

            })
        }


    });
    this.route.queryParams.subscribe(params => {
        const message = params['message'];

        if (message === 'Login effettuato con successo!') {
            this.toastMessage = message;
            this.showToast = true;
        }
      });

    this.authService.user$.subscribe(_user => {
        this.user = _user;
    })

    if(sessionStorage.getItem('prodottoId')){
        sessionStorage.removeItem('prodottoId')
    }
  }


  convertFormat(immagine: Immagine) {
    let imageFormat = immagine.nomeImmagine.split('.').pop();
    let imageInitialUrl = `data:image/${imageFormat};base64,`;
    return imageInitialUrl + immagine.dati;
  }
  calcolaTotale() {
    const totale = this.gioiello.price * this.quantitaSelezionata;
    const totaleArrotondato = totale.toFixed(2);
    const parts = totaleArrotondato.split('.');

    if (parts.length === 1) {
      return totaleArrotondato + '.00';
    } else {
        return totaleArrotondato;
    }
}

aggiungiAlCarrello() {
    if (!this.user) {
      return;
    }
    let utenteId = this.user.utenteTokenResponse.id;
    this.orderItemService.recuperaOrderItemByUtenteId(utenteId)
      .pipe(
        catchError(() => {
            console.log("qua?" );

          return this.orderItemService.creaOrderItem(utenteId);
        })
      )
      .subscribe(orderItem => {

        if (orderItem) {
            console.log(orderItem);
          this.orderItemService.addProduct(orderItem.orderItemId, this.gioiello.id, this.quantitaSelezionata).subscribe(() => {
            this.showCartToast = true;
          });
        }
      });


  }

ricordaOggetto(){
    sessionStorage.setItem('prodottoId', this.gioiello.id)
}

chiudiToast() {
    this.showToast = false;
}
chiudiCartToast(){
    this.showCartToast = false;
}


}
