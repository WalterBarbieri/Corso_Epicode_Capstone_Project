import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

    private closeModalSubject = new Subject<void>();

    closeModal$ = this.closeModalSubject.asObservable();

    private indirizzoAggiuntoSubject = new Subject<void>();

    indirizzoAggiunto$ = this.indirizzoAggiuntoSubject.asObservable();

  constructor() { }

  close() {
    this.closeModalSubject.next();
  }

  sendIndirizzoAggiuntoMessage() {
    this.indirizzoAggiuntoSubject.next();
  }
}
