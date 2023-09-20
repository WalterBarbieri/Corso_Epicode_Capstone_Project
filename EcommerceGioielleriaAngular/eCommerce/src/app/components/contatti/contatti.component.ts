import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-contatti',
  templateUrl: './contatti.component.html',
  styleUrls: ['./contatti.component.scss']
})
export class ContattiComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  inviaRichiesta(form: NgForm){
    console.log(form.value);

  }
  resetForm(form: NgForm) {
    form.reset();
  }
}
