import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { LoginPageComponent } from "./login-page/login-page.component";
import { BillPageComponent } from "./bill-page/bill-page.component";
import { LoginService } from './login-page/login-page.service';
import { CommonModule, Location } from '@angular/common';
import { PaymentPageComponent } from "./payment-page/payment-page.component";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, LoginPageComponent, BillPageComponent, CommonModule, PaymentPageComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'BBC_Frontend';
  showNavBar: boolean = false;
  customerId: number | null = null;

  constructor(private loginService: LoginService, private router: Router, private location : Location) {this.router.events.subscribe((event) => {
    if (event instanceof NavigationEnd) {
      this.showNavBar = this.router.url !== '/login';
      this.customerId = this.loginService.getCustomerId();
    }
  });}
  

  get isLoggedIn(): boolean {
    return this.loginService.isLoggedIn();
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['/login']).then(() => {
      window.history.replaceState({}, '', '/login');
    });
    this.showNavBar = false;
  }

  goBack() {
    this.location.back();
  }

}
