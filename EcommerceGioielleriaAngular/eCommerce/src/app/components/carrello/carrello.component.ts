import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthData } from 'src/app/auth/auth.interface';
import { AuthService } from 'src/app/auth/auth.service';
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
    orderItem!: OrderItem;
    utente!: Utente;
    indirizzoToasts: { [key: string]: boolean } = {};
    sub!: Subscription;
    isModalOpen = false;
    private modalCloseSubscription!: Subscription;
    private addedIndirizzoSubscription!: Subscription;

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
            .recuperaOrderItemByUtenteId(this.user!.utenteTokenResponse.id)
            .subscribe((response) => {
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

    openToast(id: string) {
        this.indirizzoToasts[id] = true;
    }
    closeToast(id: string) {
        this.indirizzoToasts[id] = false;
    }
    toggleModal() {
        this.isModalOpen = !this.isModalOpen;
    }
    eliminaIndirizzo(id: string) {
        this.sub = this.indirizzoService.eliminaIndirizzo(id).subscribe(
            () => {
                this.utente?.indirizzi?.splice(
                    this.utente?.indirizzi?.findIndex(
                        (indirizzo) => indirizzo.id === id
                    ),
                    1
                );
            },
            (error) => {
                console.error(error.error);
            }
        );
    }
}
