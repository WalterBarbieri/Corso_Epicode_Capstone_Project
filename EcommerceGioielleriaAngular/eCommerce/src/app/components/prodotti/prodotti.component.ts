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
    gioielli!: Gioiello[];
    sub!: Subscription;
    categoriaSelezionata: string = '';

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
        this.gioielloService.recuperaProdottyByCategoria(this.categoriaSelezionata).subscribe((response: any) => {
            console.log(response.content);

            this.gioielli = response.content;

        })

    } else {
        this.gioielloService.recuperaProdotti().subscribe((response: any) => {
            console.log(response.content);

            this.gioielli = response.content;

        })
    }
  }

}
