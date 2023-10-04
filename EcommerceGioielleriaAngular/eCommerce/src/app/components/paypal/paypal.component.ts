import {
    Component,
    ElementRef,
    NgZone,
    OnInit,
    ViewChild,
} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, switchMap } from 'rxjs';
import { AuthData } from 'src/app/auth/auth.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { Ordine } from 'src/app/models/ordine.interface';
import { Utente } from 'src/app/models/utente.interface';
import { OrderItemService } from 'src/app/service/order-item.service';
import { OrdineService } from 'src/app/service/ordine.service';
import { PaypalService } from 'src/app/service/paypal.service';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-paypal',
    templateUrl: './paypal.component.html',
    styleUrls: ['./paypal.component.scss'],
})
export class PaypalComponent implements OnInit {
    amount!: number;
    user!: AuthData | null;
    utente!: Utente;
    sub!: Subscription;
    ordine!: Ordine;
    prodottiArray: { prodotto: any; quantita: number }[] = [];

    @ViewChild('paymentRef', { static: true }) paymentRef!: ElementRef;

    constructor(
        private route: ActivatedRoute,
        private payment: PaypalService,
        private authService: AuthService,
        private userService: UserService,
        private ordineService: OrdineService,
        private orderItemService: OrderItemService,
        private router: Router,
        private ngZone: NgZone
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

        this.route.paramMap
            .pipe(
                switchMap((params) => {
                    const ordineId = params.get('ordineId');
                    return this.ordineService.recuperaOrdineByOrdineId(
                        ordineId!
                    );
                })
            )
            .subscribe((ordine: Ordine) => {
                this.ordine = ordine;
                console.log(ordine);

                this.amount = ordine.totale;

                this.recuperaDettagliProdotti();

                this.initiatePaypal();
            });
    }

    ngOnDestroy(): void {
        if (this.ordine && this.ordine.statoOrdine === 'PENDENTE') {
            console.log('ngdestroy');

            console.log(this.ordine.statoOrdine);

            this.eliminaOrdine();
        }
    }

    initiatePaypal() {
        if (this.ordine) {
            window.paypal
                .Buttons({
                    style: {
                        layout: 'horizontal',
                        color: 'blue',
                        label: 'paypal',

                        tagline: false,
                    },
                    createOrder: (data: any, actions: any) => {
                        return actions.order.create({
                            purchase_units: [
                                {
                                    amount: {
                                        value: this.amount.toString(),
                                        // this.amount.toString(),
                                        currency_code: 'USD',
                                    },
                                },
                            ],
                        });
                    },
                    onApprove: (data: any, actions: any) => {
                        return actions.order.capture().then((details: any) => {
                            if (details.status === 'COMPLETED') {
                                this.payment.transactionID = details.id;
                                console.log(details);
                                this.ordinePagato();
                            }
                        });
                    },
                    onError: (error: any) => {
                        console.log(error);
                        this.pagamentoRifiutato();
                    },
                })
                .render(this.paymentRef.nativeElement);
        }
    }

    recuperaDettagliProdotti(): void {
        if (this.ordine) {
            for (const prodottoId in this.ordine.prodotti) {
                if (this.ordine.prodotti.hasOwnProperty(prodottoId)) {
                    const quantita = this.ordine?.prodotti[prodottoId] ?? 0;
                    this.orderItemService.getProdottoById(prodottoId).subscribe(
                        (dettagliProdotto) => {
                            const prodottoOrdine = {
                                prodotto: dettagliProdotto,
                                quantita: quantita,
                            };
                            this.prodottiArray.push(prodottoOrdine);
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

    ordinePagato() {
        this.ordineService
            .modificaStatoOrdine(this.ordine.ordineId, 'PAGATO')
            .subscribe((response) => {
                if (response) {
                    this.ordine = response;
                    alert(
                        "Ordine Pagato con successo, riceverete a breve una email con il riepilogo dell'acquisto"
                    );
                    this.eliminaCarrello();
                }
            });
    }

    eliminaCarrello() {
        this.ngZone.run(() => {
            this.orderItemService
                .recuperaOrderItemByUtenteId(this.utente.id)
                .subscribe((response) => {
                    if (response) {
                        this.orderItemService
                            .removeOrderItem(response.orderItemId)
                            .subscribe(() => {
                                console.log('Carrello eliminato con successo');
                                this.router.navigate(['/ordineSuccesso']);
                            });
                    }
                });
        });
    }

    pagamentoRifiutato() {
        this.ngZone.run(() => {
            this.ordineService
                .eliminaOrdine(this.ordine.ordineId)
                .subscribe(() => {
                    alert('Errore durante il pagamento, riprovare');
                    this.router.navigate(['/ordineAnnullato']);
                });
        });
    }

    eliminaOrdine() {
        this.ordineService.eliminaOrdine(this.ordine.ordineId).subscribe(() => {
            console.log(
                'Ordine eliminato perché il pagamento non è stato completato'
            );
        });
    }
}
