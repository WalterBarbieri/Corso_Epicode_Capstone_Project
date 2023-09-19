import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthData } from './auth.interface';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    jwtHelper = new JwtHelperService();

    baseUrl = environment.baseURL;

    private authSubj = new BehaviorSubject<null | AuthData>(null);

    utente!: AuthData;

    user$ = this.authSubj.asObservable();

    timeOutLogout: any;

    errorMessage: string = "";

    constructor(private http: HttpClient, private router: Router) {
        this.restore();

    }

    login(data: { email: string; password: string }) {
        return this.http.post<AuthData>(`${this.baseUrl}auth/login`, data).pipe(
            tap((data) => {
                this.authSubj.next(data);
                this.utente = data;
                console.log(this.utente);
                localStorage.setItem('user', JSON.stringify(data));
            }),
            catchError((error) => {
                console.error(error.message);
                if (error instanceof HttpErrorResponse) {
                    console.error('Status Code: ', error.status);
                    console.error('Response Body: ', error.error);
                    this.errorMessage = error.error.message;
                    return throwError(() => new Error(error.message));
                }
                return throwError(() => new Error('Errore nella chiamata'));
            })
        );
    }

    restore() {

        const user = localStorage.getItem('user');
        if (!user) {
            return;
        }

        const userData: AuthData = JSON.parse(user);
        console.log("tokerecuperato: " + userData.token);
        console.log("nome token: " + userData.utenteTokenResponse.nome);

        if (this.jwtHelper.isTokenExpired(userData.token)) {
            console.log(userData.token);

            console.log("token è expired");

            return;
        }

        this.authSubj.next(userData);

        this.autoLogout(userData);
    }

    signUp(data: {
        nome: string;
        cognome: string;
        dataNascita: Date;
        email: string;
        password: string;
    }) {
        return this.http
            .post(`${this.baseUrl}auth/register`, data)
            .pipe(catchError((err) => throwError(() => {

           new Error(err.error);
        this.errorMessage = err.error.message;   }
           )));
    }

    logout() {
        this.authSubj.next(null);
        localStorage.removeItem('user');
        this.router.navigate(['/']);

        if (this.timeOutLogout) {
            clearTimeout(this.timeOutLogout);
        }
    }

    autoLogout(data: AuthData) {
        const expirationDate = this.jwtHelper.getTokenExpirationDate(
            data.token
        ) as Date;
        const expirationMillisecond =
            expirationDate.getTime() - new Date().getTime();
        this.timeOutLogout = setTimeout(() => {
            this.logout();
        }, expirationMillisecond);
    }
}
