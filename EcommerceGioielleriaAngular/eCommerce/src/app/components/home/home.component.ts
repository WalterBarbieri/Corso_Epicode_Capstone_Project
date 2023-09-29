import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
    baseUrl = environment.baseURL;
    showToast = false;
    toastMessage: string = '';

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
        const message = params['message'];

        if (message === 'Login effettuato con successo!') {
            this.toastMessage = message;
            this.showToast = true;
        }
      });
  }

  chiudiToast() {
    this.showToast = false;
}

}
