import { Component, ElementRef, OnInit } from '@angular/core';
import { AuthData } from 'src/app/auth/auth.interface';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

    isOpen: boolean = false;

    user!:AuthData | null;

  constructor(private authService: AuthService, private elementRef: ElementRef) {
  }

  ngOnInit(): void {
    this.authService.user$.subscribe(_user => {
        this.user = _user;
    })

    document.addEventListener('click', this.onClickOutside.bind(this))
  }

  ngOnDestroy(): void {
    document.removeEventListener('click', this.onClickOutside.bind(this))
  }

  rechargeUser() {
    this.authService.user$.subscribe(_user => {
        this.user = _user;
    })

  }

  toggleHamburgerMenu(){
    if (window.innerWidth <= 991) {
        this.isOpen = !this.isOpen;
    }
    if (!this.isOpen) {
        document.querySelector('.navbar-collapse.show')?.classList.remove('show');
        const dropdownMenu = document.querySelector('.dropdown-menu.show');
      if (dropdownMenu) {
        dropdownMenu.classList.remove('show');
      }
    }

  }
  logout(){
    this.authService.logout();
  }

  onClickOutside(event: Event) {
    if(!this.elementRef.nativeElement.contains(event.target)){
        if(this.isOpen) {
            this.toggleHamburgerMenu();
        }
    }
  }


}
