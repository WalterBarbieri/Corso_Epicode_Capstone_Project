import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { catchError, of } from 'rxjs';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
    error!: string;
    showAlert = true;

    constructor(private authService: AuthService, private router: Router) {}

    ngOnInit(): void {}

    accedi(form: NgForm) {
        console.log(form.value);

        this.authService
            .login(form.value)
            .pipe(
                catchError((error) => {
                    console.log(error);
                    this.error = this.authService.errorMessage;
                    form.reset;
                    return of(null);
                })
            )
            .subscribe((response) => {
                if (response) {
                    sessionStorage.setItem('isFirstLoad', 'true');
                    this.router.navigate(['/']);
                }
            });
    }
    redirectToRegister() {
        this.router.navigate(['/register']);
      }

      chiudiAlert(){
        this.showAlert = false;
        this.error = '';
        this.showAlert = true;
      }
}
