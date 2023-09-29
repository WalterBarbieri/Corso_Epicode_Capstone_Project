import { Component, OnInit } from '@angular/core';
import { Gioiello } from 'src/app/models/gioiello.interface';
import { GioielloService } from 'src/app/service/gioiello.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GioielloPayload } from 'src/app/models/gioiello-payload.interface';
import { Subscription } from 'rxjs';
import { Immagine } from 'src/app/models/immagine.interface';

@Component({
  selector: 'app-adminpage',
  templateUrl: './adminpage.component.html',
  styleUrls: ['./adminpage.component.scss']
})
export class AdminpageComponent implements OnInit {

    showProdotti = false;
    showUtenti = false;
    showOrdini = false;

  constructor() {}

  ngOnInit(): void {}

  gestisciProdotti(){
    this.showProdotti = true;
    this.showUtenti = false;
    this.showOrdini = false;
  }
  gestisciUtenti(){
    this.showProdotti = false;
    this.showUtenti = true;
    this.showOrdini = false;
  }
  gestisciOrdini(){
    this.showProdotti = false;
    this.showUtenti = false;
    this.showOrdini = true;
  }
  chiudiTutto(){
    this.showProdotti = false;
    this.showUtenti = false;
    this.showOrdini = false;
  }
}
