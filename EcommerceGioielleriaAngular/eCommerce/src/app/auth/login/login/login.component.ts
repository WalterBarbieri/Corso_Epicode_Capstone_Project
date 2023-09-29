import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth.service';
import { Router, ActivatedRoute } from '@angular/router';
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
    showToast = false;
    toastMessage: string = '';
    gioielloRicordato: string | null = sessionStorage.getItem('prodottoId');

    constructor(
        private authService: AuthService,
        private router: Router,
        private route: ActivatedRoute
    ) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            const message = params.get('message');

            if (message === 'Registrazione effettuata con successo!') {
                this.toastMessage = message;
                this.showToast = true;
            }
        });
    }

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

                    if (!this.gioielloRicordato) {
                        this.router.navigate(['/'], {
                            queryParams: {
                                message: 'Login effettuato con successo!',
                            },
                        });
                    } else {
                        this.router.navigate(['/dettaglio', this.gioielloRicordato], {
                            queryParams: {
                                message: 'Login effettuato con successo!',
                            },
                        });
                    }
                }
            });
    }
    redirectToRegister() {
        this.router.navigate(['/register']);
    }

    chiudiAlert() {
        this.showAlert = false;
        this.error = '';
        this.showAlert = true;
    }

    chiudiToast() {
        this.showToast = false;
    }
}
