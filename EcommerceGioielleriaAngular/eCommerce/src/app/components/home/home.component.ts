import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
    baseUrl = environment.baseURL;
    users!: any[];
    sub!: Subscription;
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
/**
    this.sub = this.recuperaProdotti().subscribe((testUsers: any[]) => {
        console.log(testUsers);

        this.users = testUsers;
    })
    console.log(this.users);
    */

  }

  recuperaProdotti():Observable<any>{
    console.log("chiamata partita");

    return this.http.get<any[]>(`${this.baseUrl}utenti`)
  }

}
