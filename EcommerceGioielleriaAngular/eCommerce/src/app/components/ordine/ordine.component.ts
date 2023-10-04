import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, switchMap } from 'rxjs';
import { AuthData } from 'src/app/auth/auth.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { Indirizzo } from 'src/app/models/indirizzo.interface';
import { OrderItem } from 'src/app/models/order-item.interface';
import { OrdinePayload } from 'src/app/models/ordine-payload.interface';
import { Utente } from 'src/app/models/utente.interface';
import { OrderItemService } from 'src/app/service/order-item.service';
import { OrdineService } from 'src/app/service/ordine.service';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-ordine',
    templateUrl: './ordine.component.html',
    styleUrls: ['./ordine.component.scss'],
})
export class OrdineComponent implements OnInit {
    user!: AuthData | null;
    utente!: Utente;
    sub!: Subscription;
    orderItem: OrderItem | undefined;
    prodottiArray: { prodotto: any; quantita: number }[] = [];
    totaleCarrello: number = 0;
    indirizzoConsegna: Indirizzo | null = null;

    constructor(
        private authService: AuthService,
        private userService: UserService,
        private orderItemService: OrderItemService,
        private router: ActivatedRoute,
        private ordineService: OrdineService,
        private route: Router
    ) {}

    ngOnInit(): void {
        this.authService.user$
            .pipe(
                switchMap((_user) => {
                    this.user = _user;
                    if (this.user) {
                        return this.userService.recuperaUtenteById(
                            this.user?.utenteTokenResponse.id
                        );
                    }
                    throw new Error('Utente non trovato');
                })
            )
            .subscribe((currentUtente: Utente) => {
                this.utente = currentUtente;

                this.router.paramMap.subscribe((params) => {
                    const id = params.get('id');

                    if (this.utente) {
                        this.utente.indirizzi.forEach((element) => {
                            if (element.id === id) {
                                this.indirizzoConsegna = element;
                                console.log(this.indirizzoConsegna);

                            }
                        });
                    }
                });
            });

        this.recuperaOrderItem();
    }

    recuperaOrderItem(): void {
        if (this.user && this.user.utenteTokenResponse?.id) {
            const userId = this.user.utenteTokenResponse.id;
            this.orderItemService.recuperaOrderItemByUtenteId(userId).subscribe(
                (response: OrderItem) => {
                    this.orderItem = response;
                    this.recuperaDettagliProdotti();
                },
                (error) => {
                    console.error("Errore nel recupero dell'OrderItem", error);
                }
            );
        } else {
            console.error('ID utente non definito.');
        }
    }

    recuperaDettagliProdotti(): void {
        if (this.orderItem) {
            for (const prodottoId in this.orderItem.prodotti) {
                if (this.orderItem.prodotti.hasOwnProperty(prodottoId)) {
                    const quantita = this.orderItem?.prodotti[prodottoId] ?? 0;
                    this.orderItemService.getProdottoById(prodottoId).subscribe(
                        (dettagliProdotto) => {
                            const prodottoCarrello = {
                                prodotto: dettagliProdotto,
                                quantita: quantita,
                            };
                            this.prodottiArray.push(prodottoCarrello);
                            this.calcolaTotaleCarrello();
                        },
                        (error) => {
                            console.error(
                                'Errore nel recupero dei dettagli del prodotto',
                                error
                            );
                        }
                    );
                }
            }
        }
    }
    calcolaTotaleCarrello(): void {
        this.totaleCarrello = this.prodottiArray.reduce(
            (totale, prodotto) =>
                totale + prodotto.quantita * prodotto.prodotto.price,
            0
        );

    }

    arrotondaTotale(totale: number) {
        const totaleArrotondato = totale.toFixed(2);
        const parts = totaleArrotondato.split('.');

        if (parts.length === 1) {
            return totaleArrotondato + '.00';
        } else {
            return totaleArrotondato;
        }
    }

    creaOrdine(){
        const ordinePayload: OrdinePayload = {
            prodotti: this.orderItem!.prodotti,
            utente: this.utente,
            indirizzoConsegna: this.indirizzoConsegna!
        }
        const importo = parseFloat(this.arrotondaTotale(this.totaleCarrello * 0.78));
        const iva = parseFloat(this.arrotondaTotale(this.totaleCarrello * 0.22));
        const totale = parseFloat(this.arrotondaTotale(this.totaleCarrello));
        this.ordineService.creaOrdine(ordinePayload, importo, iva, totale).subscribe((response) => {
            const ordineId = response.ordineId;
            this.route.navigate(['/paypal', ordineId])

        });
    }
}
