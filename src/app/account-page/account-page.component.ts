import { Component, OnInit } from '@angular/core';
import { CustomerService } from './customer.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-account-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './account-page.component.html',
  styleUrl: './account-page.component.css'
})
export class AccountPageComponent implements OnInit {
  customerId!: number; 
  customerDetails: any;
  isLoading: boolean = true;

  constructor(private customerService: CustomerService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.customerId = Number(this.route.snapshot.paramMap.get('customerId'));
    console.log('Retrieved customerId:', this.customerId);
    this.loadCustomerDetails();
  }

  loadCustomerDetails() {
    this.customerService.getCustomerDetails(this.customerId).subscribe({
      next: (data) => {
        this.customerDetails = data;
        console.log('Customer details loaded:', this.customerDetails);
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading customer details:', error);
        this.isLoading = false;
      }
    });
  }
}