import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth.service';
import { Router } from '@angular/router';
import { NgForm} from '@angular/forms'
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
    error!: string;

    showAlert = true;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  register(form: NgForm) {
    console.log(form.value);

    this.authService.signUp(form.value).pipe(
        catchError(() => {
            console.error(this.authService.errorMessage);

            this.error = this.authService.errorMessage;
            form.reset;
            return of(null);

        })
    ).subscribe(response => {
        if (response) {
            this.router.navigate(['/login'])
        }
    })

  }

  redirectToLogin(){
    this.router.navigate(['/login'])
  }

  chiudiAlert(){
    this.showAlert = false;
    this.error = '';
    this.showAlert = true;
  }

}
