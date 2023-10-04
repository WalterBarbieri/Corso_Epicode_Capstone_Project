import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthData } from 'src/app/auth/auth.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { Immagine } from 'src/app/models/immagine.interface';
import { Indirizzo } from 'src/app/models/indirizzo.interface';
import { OrderItem } from 'src/app/models/order-item.interface';
import { Utente } from 'src/app/models/utente.interface';

import { ModalService } from 'src/app/service/modal.service';
import { OrderItemService } from 'src/app/service/order-item.service';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-carrello',
    templateUrl: './carrello.component.html',
    styleUrls: ['./carrello.component.scss'],
})
export class CarrelloComponent implements OnInit {
    user!: AuthData | null;
    orderItem: OrderItem | undefined;
    prodottiArray: { prodotto: any; quantita: number }[] = [];

    utente!: Utente;
    sub!: Subscription;
    isModalOpen = false;
    private modalCloseSubscription!: Subscription;
    private addedIndirizzoSubscription!: Subscription;
    indirizzoSelezionato: Indirizzo | null = null;
    totaleCarrello: number = 0;
    showProdottiAssentiToast = false;
    showIndirizzoAssenteToast = false;
    private timeout: any;

    constructor(
        private authService: AuthService,
        private orderItemService: OrderItemService,
        private modalService: ModalService,
        private userService: UserService,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.authService.user$.subscribe((_user) => {
            this.user = _user;
            if (this.user) {
                this.sub = this.userService
                    .recuperaUtenteById(this.user?.utenteTokenResponse.id)
                    .subscribe((currentUtente: Utente) => {
                        this.utente = currentUtente;
                    });
            }
        });

        this.recuperaOrderItem();

        this.modalCloseSubscription = this.modalService.closeModal$.subscribe(
            () => {
                this.toggleModal();
            }
        );

        this.addedIndirizzoSubscription =
            this.modalService.indirizzoAggiunto$.subscribe(() => {
                this.authService.user$.subscribe((_user) => {
                    this.user = _user;
                    if (this.user) {
                        this.sub = this.userService
                            .recuperaUtenteById(
                                this.user?.utenteTokenResponse.id
                            )
                            .subscribe((currentUtente: Utente) => {
                                this.utente = currentUtente;
                            });
                    }
                });
            });
    }
    ngOnDestroy(): void {
        this.modalCloseSubscription.unsubscribe();
    }

    toggleModal() {
        this.isModalOpen = !this.isModalOpen;
    }

    convertFormat(immagine: Immagine) {
        let imageFormat = immagine.nomeImmagine.split('.').pop();
        let imageInitialUrl = `data:image/${imageFormat};base64,`;
        return imageInitialUrl + immagine.dati;
    }

    selectCheckbox(indirizzo: Indirizzo) {
        if (this.indirizzoSelezionato === indirizzo) {
            this.indirizzoSelezionato = null;
        } else {
            this.indirizzoSelezionato = indirizzo;
        }
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

    increaseQuantity(prodotto: any): void {
        if (prodotto.quantita < prodotto.prodotto.quantita) {
            this.orderItemService
                .increaseProductQuantity(
                    this.orderItem!.orderItemId,
                    prodotto.prodotto.id
                )
                .subscribe(
                    () => {
                        prodotto.quantita++;
                        this.calcolaTotaleCarrello();
                    },
                    (error) => {
                        console.error(
                            "Errore nell'aumento della quantità del prodotto",
                            error
                        );
                    }
                );
        } else {
            alert('Quantità richiesta superiore a quella in magazzino');
        }
    }

    decreaseQuantity(prodotto: any): void {

        if (prodotto.quantita > 1) {
            this.orderItemService
                .decreaseProductQuantity(
                    this.orderItem!.orderItemId,
                    prodotto.prodotto.id
                )
                .subscribe(
                    () => {
                        prodotto.quantita--;
                        this.calcolaTotaleCarrello();
                    },
                    (error) => {
                        console.error(
                            'Errore nella diminuzione della quantità del prodotto',
                            error
                        );
                    }
                );
        } else {
            alert('Quantità Minima, impossibile ridurre ulteriormente');
        }
    }

    removeProdotto(prodotto: any) {
        this.orderItemService
            .removeProduct(this.orderItem!.orderItemId, prodotto.prodotto.id)
            .subscribe(() => {
                this.prodottiArray = [];
                this.recuperaOrderItem();
                this.calcolaTotaleCarrello();
                alert('Prodotto eliminato con successo');
            });
    }

    isEmpty(obj: any): boolean {
        return obj && Object.keys(obj).length === 0;
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

    procediOrdine(){
        if(!this.orderItem || this.isEmpty(this.orderItem.prodotti)){
            this.showProdottiAssentiToast = true;
            this.timeout = setTimeout(() => {
                this.showProdottiAssentiToast = false;
            }, 4000)

        } else if (this.indirizzoSelezionato == null){
            this.showIndirizzoAssenteToast = true;
            this.timeout = setTimeout(() => {
                this.showIndirizzoAssenteToast = false;
            }, 4000);


        } else {
            this.router.navigate(['/ordine', this.indirizzoSelezionato.id]);

        }
    }

    chiudiProdottiAssentiToast() {
        this.showProdottiAssentiToast = false;
        clearTimeout(this.timeout);
    }
    chiudiIndirizzoAssenteToast() {
        this.showIndirizzoAssenteToast = false;
        clearTimeout(this.timeout);

    }
}
