import { Component, OnInit } from '@angular/core';
import { Subscription, catchError, of } from 'rxjs';
import { AuthData } from 'src/app/auth/auth.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { Immagine } from 'src/app/models/immagine.interface';
import { Indirizzo } from 'src/app/models/indirizzo.interface';
import { OrderItem } from 'src/app/models/order-item.interface';
import { Utente } from 'src/app/models/utente.interface';
import { IndirizzoService } from 'src/app/service/indirizzo.service';
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
    orderItem!: OrderItem | null;
    utente!: Utente;
    indirizzoToasts: { [key: string]: boolean } = {};
    sub!: Subscription;
    isModalOpen = false;
    private modalCloseSubscription!: Subscription;
    private addedIndirizzoSubscription!: Subscription;
    indirizzoSelezionato: Indirizzo | null = null;

    constructor(
        private authService: AuthService,
        private orderItemService: OrderItemService,
        private indirizzoService: IndirizzoService,
        private modalService: ModalService,
        private userService: UserService
    ) {}

    ngOnInit(): void {
        this.authService.user$.subscribe((_user) => {
            this.user = _user;
        });

        this.orderItemService
            .recuperaOrderItemByUtenteId(this.user!.utenteTokenResponse.id).pipe(
                catchError(() => {
                    this.orderItem = null;
                    return of(null);
                })
            ).subscribe((response) => {
                this.orderItem = response;
                this.utente = response.utente;
                console.log(this.orderItem);
                console.log(this.utente);
            });

        this.indirizzoToasts = {};

        if (this.utente && this.utente.indirizzi) {
            this.utente.indirizzi.forEach((indirizzo) => {
                this.indirizzoToasts[indirizzo.id] = false;
            });
        }

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

                                console.log(this.utente);
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
}
