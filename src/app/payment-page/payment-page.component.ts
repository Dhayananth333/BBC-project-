import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PaymentService } from './payment.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BillPageService } from '../bill-page/bill-page.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-payment-page',
  templateUrl: './payment-page.component.html',
  standalone: true,
  imports: [CommonModule, FormsModule],
  styleUrls: ['./payment-page.component.css']
})
export class PaymentPageComponent implements OnInit {
  totalAmount: any;
  netAmount: number = 0;
  cardTopUpAmount: number = 0;

  message: string = '';

  isMessageLoading: boolean = true;

  constructor(
    private paymentService: PaymentService,
    private route: ActivatedRoute,
    private router : Router 

  ) {}

  successMessage: string = '';
  errorMessage: string = '';
  paymentMethods: string[] = ['Credit Card', 'Debit Card'];
  selectedMethod: string = '';
  
  cardDetails = {
    cardNumber: '',
    expiryDate: '',
    cvv: ''
  };
  
  cardNumberError: string = '';
  expiryDateError: string = '';
  cvvError: string = '';
  
  otp: string = '';
  otpError: string = '';
  otpField: boolean = false;

  customerId: number | null = null;
  billId!: number;
  billDetails: any;

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.billId = +params['billId'];
      this.customerId = +params['customerId'];

      this.paymentService.getBillDetails(this.billId).subscribe({
        next: (bill) => {
          this.billDetails = bill;
          console.log('Bill Details:', this.billDetails);
        },
        error: (error) => {
          console.error('Error fetching bill details:', error);
        }
      });
    });
  }

  calculateNetAmount() {
    const totalAmount = this.billDetails?.amountDue || 0;

    let discountedAmount = totalAmount - (totalAmount * 5 / 100);
    const dueDate = new Date(this.billDetails?.billDueDate);
    const currentDate = new Date();
    if (currentDate <= dueDate) {
        discountedAmount = discountedAmount - (discountedAmount * 5 / 100);
    }

    this.netAmount = discountedAmount;
}


  validateCardNumber() {
    const cardNumberPattern = /^[0-9]{16}$/;
    if (!cardNumberPattern.test(this.cardDetails.cardNumber)) {
      this.cardNumberError = 'Invalid card number. Must be 16 digits.';
    } else {
      this.cardNumberError = '';
    }
  }

  validateExpiryDate() {
    const expiryDatePattern = /^(0[1-9]|1[0-2])\/?([0-9]{2})$/;
    if (!expiryDatePattern.test(this.cardDetails.expiryDate)) {
      this.expiryDateError = 'Invalid expiry date format. Use MM/YY.';
    } else {
      this.expiryDateError = '';
    }
  }

  validateCvv() {
    const cvvPattern = /^[0-9]{3}$/;
    if (!cvvPattern.test(this.cardDetails.cvv)) {
      this.cvvError = 'Invalid CVV. Must be 3 digits.';
    } else {
      this.cvvError = '';
    }
  }

  verifyCard() {
    const requestBody = {
      customerId: this.customerId,
      cardNumber: this.cardDetails.cardNumber,
      expiryDate: this.cardDetails.expiryDate,
      cvv: this.cardDetails.cvv
    };

    this.paymentService.verifyCard(requestBody).subscribe({
      next: (response) => {
        this.successMessage = response.message;
        console.log("Success message for card verification", this.successMessage);
        this.errorMessage = '';
        this.otpField = true;

      },
      error: (errorResponse) => {
        this.errorMessage = errorResponse.error.message;
        console.log("Error message from verify card", this.errorMessage);
        this.successMessage = '';
        this.otpField = true;  
        
      }
    });
  }


  sendOtp() {
    this.paymentService.sendOtp(this.customerId!).subscribe({
      next: (response: { message: string }) => {
        this.isMessageLoading = false;
        console.log('OTP sent successfully:', response);
        this.otpField = true;
        this.otpError = '';
      },
      error: (errorResponse: any) => {
        this.isMessageLoading = false;
        console.log("Error sending OTP", errorResponse);
        this.otpError = 'Error sending OTP. Please try again.';
        this.otpField = false;
      }
    });
  }

  verifyOtp() {
    this.paymentService.validateOtp(this.customerId!, this.otp).subscribe({
      next: (response: { message: string }) => {
        this.isMessageLoading = false;
        this.successMessage = response.message;
        this.otpError = '';
        this.processPayment();
        this.otpField = true;
        
      },
      error: (errorResponse: any) => {
        this.successMessage = '';
        this.errorMessage = errorResponse.error.message;
        console.log(errorResponse);
        this.otpError = 'Wrong OTP, Try again';
        this.otpField = false;
      }
    });
  }

  processPayment() {
    
    const paymentRequest = {
        customerId: this.customerId,
        billId: this.billId,
        amount: this.netAmount,
        cardNumber: this.cardDetails.cardNumber,
    };

    this.paymentService.processPayment(paymentRequest).subscribe({
        next: (response: any) => { 
            this.successMessage = response.message || 'Payment processed successfully.';
            this.errorMessage = '';
            console.log('Payment response:', response);
            this.router.navigate(['/bill', this.customerId]);
        },
        error: (errorResponse: any) => {
           
            this.errorMessage = errorResponse.error.message;
            this.successMessage = '';
            console.log('Payment error:', errorResponse);
            this.otpField = false;
            this.otp = "";
        }
    });
  }

  
}
