import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Comune } from 'src/app/models/comune.interface';
import { Indirizzo } from 'src/app/models/indirizzo.interface';
import { IndirizzoService } from 'src/app/service/indirizzo.service';
import { NavbarComponent } from '../navbar/navbar.component';
import { IndirizzoPayload } from 'src/app/models/indirizzo-payload.interface';

@Component({
  selector: 'app-modalindirizzo',
  templateUrl: './modalindirizzo.component.html',
  styleUrls: ['./modalindirizzo.component.scss']
})
export class ModalindirizzoComponent implements OnInit {

indirizzoForm: FormGroup;

regioneSelezionata = false;

listaComuni!: Comune[] ;

listaComuniFiltrata!: Comune[];

sub!: Subscription;

province: String[] = [];

email!: string;


  constructor(private formBuilder: FormBuilder, private indirizzoService: IndirizzoService) {
    this.indirizzoForm = this.formBuilder.group({
        regione: [null, Validators.required],
        provincia: [null, Validators.required],
        comune: [null, Validators.required],
        via: [null, Validators.required],
      civico: [null, Validators.required],
      localita: [null, Validators.required],
      cap: [null, Validators.required]
    })
  }

  ngOnInit(): void {
    this.sub = this.indirizzoService.getComuni().subscribe((comuni: Comune[]) => {
        this.listaComuni = comuni;
        console.log(comuni);
        this.province = Array.from(new Set (this.listaComuni.map((comune) => comune.nomeProvincia)))
        console.log(this.province);


    })
    const userJson = JSON.parse(localStorage.getItem('user') || '{}');
    this.email = userJson.utenteTokenResponse.email;
  }

  onSubmit(){
    if(this.indirizzoForm.valid) {
        const indirizzo: IndirizzoPayload = {
            via: this.indirizzoForm.get('via')?.value,
            civico: this.indirizzoForm.get('civico')?.value,
            localita: this.indirizzoForm.get('localita')?.value,
            cap: this.indirizzoForm.get('cap')?.value,
            nomeComune: this.indirizzoForm.get('comune')?.value,
            email: this.email
        }

        this.indirizzoService.aggiungiIndirizzo(indirizzo).subscribe((response) => {
            console.log("Indirizzo aggiunto con successo", response);

        }, (error)=> {
            console.error('Errore durante l\'inserimento dell\'indirizzo', error);

        } )
    }
  }

  onRegioneChange() {
    const regioneControl = this.indirizzoForm.get('regione')?.value;
    this.regioneSelezionata = !! regioneControl;
    const provinciaControl = this.indirizzoForm.get('provincia');
    const comuneControl = this.indirizzoForm.get('comune');

    if (this.regioneSelezionata && regioneControl != '') {
        const provinceFiltrate = Array.from(new Set(this.listaComuni.filter((comune) => comune.nomeRegione == regioneControl).map((comune) => comune.nomeProvincia)));
        provinciaControl?.setValue(null);
        comuneControl?.setValue(null);

        this.province = provinceFiltrate;
    }  else {
        provinciaControl?.setValue(null);
        this.province = Array.from(new Set (this.listaComuni.map((comune) => comune.nomeProvincia)))


    }
  }

  onProvinciaChange() {
    const provinciaSelezionata = this.indirizzoForm.get('provincia')?.value;
    const regioneControl = this.indirizzoForm.get('regione');
    const comuneControl = this.indirizzoForm.get('comune');

    if (provinciaSelezionata) {
      const comune = this.listaComuni.find((comune) => comune.nomeProvincia === provinciaSelezionata);
      if (comune) {
        regioneControl?.setValue(comune.nomeRegione);
        this.regioneSelezionata = true;
        comuneControl?.setValue(null);
      }
    }
  }

  onComuneChange() {
    const comuneController = this.indirizzoForm.get('comune');
    const comuneValue = comuneController?.value;

    if(comuneValue.length >= 2) {
        const provinciaControl = this.indirizzoForm.get('provincia')?.value;
    const regioneControl = this.indirizzoForm.get('regione')?.value;

    this.indirizzoService.findComuneByDenominazione(comuneValue, provinciaControl, regioneControl).subscribe((comuni: Comune[]) => {
        this.listaComuniFiltrata = comuni;


    })
    }

  }

  selectComune(comune: Comune) {
    this.indirizzoForm.get('comune')?.setValue(comune.denominazione);
    this.indirizzoForm.get('provincia')?.setValue(comune.nomeProvincia);
    this.indirizzoForm.get('regione')?.setValue(comune.nomeRegione);

    this.listaComuniFiltrata = [];

  }

}
