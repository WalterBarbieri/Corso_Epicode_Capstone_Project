import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Gioiello } from 'src/app/models/gioiello.interface';
import { Immagine } from 'src/app/models/immagine.interface';
import { GioielloService } from 'src/app/service/gioiello.service';

@Component({
  selector: 'app-prodotti',
  templateUrl: './prodotti.component.html',
  styleUrls: ['./prodotti.component.scss']
})
export class ProdottiComponent implements OnInit {
    gioielli: Gioiello[] = [];
    pages: number[] = [];
    categoriaSelezionata: string = '';
    sortSelezionato: string = '';
    paginaSelezionata: number = 1;

  constructor(private gioielloService: GioielloService,private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
        let categoriaParam = params.get('categoria');
        this.categoriaSelezionata = categoriaParam !== null ? categoriaParam : '';
        this.filterByCategoria();
    })

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
