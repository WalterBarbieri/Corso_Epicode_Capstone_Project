import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    Router,
    RouterStateSnapshot,
    UrlTree,
} from '@angular/router';
import { Observable, map, take } from 'rxjs';
import { AuthService } from './auth.service';
import { AuthData } from './auth.interface';

@Injectable({
    providedIn: 'root',
})
export class AuthGuard implements CanActivate {
    constructor(private authService: AuthService, private router: Router) {}
    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ):
        | Observable<boolean | UrlTree>
        | Promise<boolean | UrlTree>
        | boolean
        | UrlTree {
            return this.authService.user$.pipe(
                take<AuthData | null>(1),
                map((authData: AuthData | null) => {
                    if (authData && authData.utenteTokenResponse) {
                        const ruolo = authData.utenteTokenResponse.ruolo;


                        if (route.data['roles'] && route.data['roles'].includes(ruolo)) {
                            return true;
                        } else {
                            alert('Non sei autorizzato a vedere questo contenuto')
                            return this.router.createUrlTree(['/']);
                        }
                    }

                    alert('Non puoi accedere a questo contenuto senza effettuare il login')
                    return this.router.createUrlTree(['/login']);
                })
            );
    }
}
