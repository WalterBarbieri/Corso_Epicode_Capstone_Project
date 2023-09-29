import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Gioiello } from 'src/app/models/gioiello.interface';
import { Immagine } from 'src/app/models/immagine.interface';
import { GioielloService } from 'src/app/service/gioiello.service';

@Component({
  selector: 'app-gestione-prodotti',
  templateUrl: './gestione-prodotti.component.html',
  styleUrls: ['./gestione-prodotti.component.scss']
})
export class GestioneProdottiComponent implements OnInit {


    selectedImages: File [] = [];
    previewImages: string [] = [];
    newProductForm!: FormGroup;

    gioielli!: Gioiello[];
    pages: number[] = [];
    categoriaSelezionata: string = '';
    sortSelezionato: string = '';
    paginaSelezionata: number = 1;

  constructor(private gioielloService: GioielloService, private formBuilder: FormBuilder) {
    this.newProductForm = this.formBuilder.group({
        nomeProdotto: ['', Validators.required],
        price: ['', Validators.required],
        quantita: ['', Validators.required],
        categoria: ['', Validators.required],
        descrizione: ['', Validators.required],
        immagini: ['', Validators.required],
      });
   }

  ngOnInit(): void {
  }
    //METODI PER IL CARICAMENTO DI UN NUOVO PRODOTTO
  onFileSelected(event: any) {
    const files = event.target.files;
    if (files && files.length > 0) {
      for (let file of files) {
        this.selectedImages.push(file);
        const reader = new FileReader();

        reader.onload = (e: any) => {
            this.previewImages.push(e.target.result);
        }

        reader.readAsDataURL(file);

      }
    }
  }

  eliminaImmagine(index: number) {
    this.selectedImages!.splice(index, 1);
    this.previewImages.splice(index, 1)
  }

  creaProdotto() {
    if (this.newProductForm.valid && this.selectedImages!.length > 0) {
      const formData = new FormData();

      formData.append('nomeProdotto', this.newProductForm.get('nomeProdotto')?.value);
      formData.append('descrizione', this.newProductForm.get('descrizione')?.value);
      formData.append('price', this.newProductForm.get('price')?.value.toString());
      formData.append('quantita', this.newProductForm.get('quantita')?.value.toString());
      formData.append('categoria', this.newProductForm.get('categoria')?.value);

      for (const image of this.selectedImages!) {
        formData.append('immagini', image);
      }

      this.gioielloService.creaProdotto(formData).subscribe((response) => {
        console.log("Prodotto creato con successo", response);
        this.resetForm();
      }, (error) => {
        console.error('Errore durante la creazione del prodotto', error);
        console.log(error.message);
      });
    }
  }

  resetForm() {
    this.newProductForm.reset();
    this.selectedImages = [];
    this.previewImages = [];
  }

  //METDO PER LA VISUALIZZAZIONE DEI PRODOTTI
  mostraProdotti(){
    this.filterByCategoria();
  }

  convertFormat(immagine: Immagine){
    let imageFormat = immagine.nomeImmagine.split('.').pop();
    let imageInitialUrl = `data:image/${imageFormat};base64,`;
    return imageInitialUrl;
  }

  filterByCategoria() {
    if(this.categoriaSelezionata != '') {
        this.gioielloService.recuperaProdottyByCategoria(this.categoriaSelezionata, this.paginaSelezionata -1, this.sortSelezionato).subscribe((response: any) => {
            this.pages = Array.from({length: response.totalPages}, (_, i) => i + 1)

            this.gioielli = response.content;

        })

    } else {
        this.gioielloService.recuperaProdotti(this.paginaSelezionata -1, this.sortSelezionato).subscribe((response: any) => {
            this.pages = Array.from({length: response.totalPages}, (_, i) => i + 1)

            this.gioielli = response.content;

        })
    }
  }

  selectPage(page: number) {
    this.paginaSelezionata = page;

    this.filterByCategoria();
  }

}
