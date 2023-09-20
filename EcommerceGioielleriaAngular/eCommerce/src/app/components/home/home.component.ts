import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { Utente } from 'src/app/models/utente.interface';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
    baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {




  }



}
