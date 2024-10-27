import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from './login-page.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent {
  customerID: number | null = null;
  customerIDError: boolean = false;
  customerIDNotFoundError: boolean = false;
  isCustomerIDVerified: boolean = false;

  otpFieldVisible: boolean = false;
  otp: string = '';
  otpError: boolean = false; 
  otpGenerationDisabled: boolean = false; 
  otpExpired: boolean = false;

  canGenerateOtp: boolean = false;
  remainingTime: number = 0; 

  constructor(private loginService: LoginService, private router: Router) {}

  onArrowClick(): void {
  
    this.customerIDNotFoundError = false;
    this.customerIDError = false;

    if (this.customerID !== null) {
      this.checkCustomerExists(this.customerID as number);
    } else {
      this.customerIDError = true;
    }
  }

  checkCustomerExists(customerID: number): void {
    this.loginService.verifyCustomerID(customerID).subscribe({
      next: (exists: boolean) => {
        if (exists) {
          this.isCustomerIDVerified = true; 
          this.canGenerateOtp = true;
          this.otpFieldVisible = true;
          this.otpExpired = false;
          this.otpGenerationDisabled = true;
          this.startOtpCountdown(); 
        } else {
          this.canGenerateOtp = false;
          this.otpFieldVisible = false;
          this.customerIDNotFoundError = true;
        }
      },
      error: (err: any) => {
        console.error('Error verifying customer ID:', err);
        this.canGenerateOtp = false;
        this.otpFieldVisible = false;
        this.customerIDNotFoundError = true;
      },
    });
  }

  onGenerateOtp(): void {
    if (this.canGenerateOtp && !this.otpGenerationDisabled) {
      this.loginService.verifyCustomerID(this.customerID as number).subscribe({
        next: () => {
          this.startOtpCountdown(); 
          this.otpExpired = false;
        },
        error: (err: any) => {
          console.error('Error generating OTP:', err);
        }
      });
    }
  }

  startOtpCountdown(): void {
    this.remainingTime = 90; 

    const interval = setInterval(() => {
      this.remainingTime--;

      if (this.remainingTime <= 0) {
        clearInterval(interval);
        this.otpGenerationDisabled = false; 
        this.otpError = false;
        this.otpExpired = true;
      }
    }, 1000);
  }

  validateOtp(): void {
    this.loginService.validateOtp(this.customerID as number, this.otp).subscribe({
      next: (isValid: boolean) => {
        if (isValid) {
          this.loginService.login(this.customerID);
          this.otpError = false;
          console.log('Navigating to bill with customerID:', this.customerID);
          this.router.navigate(['/bill', this.customerID]);
        } else {
          this.otpError = true; 
        }
      },
      error: (err: any) => {
        console.error('Error validating OTP:', err);
        this.otpError = true; 
      },
    });
  }

  onLogout(): void {
    this.loginService.logout();
    this.router.navigate(['/login']);
  }
}
