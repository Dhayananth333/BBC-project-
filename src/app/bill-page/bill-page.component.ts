import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { BillPageService } from './bill-page.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-bill-page',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, DatePipe,FormsModule],
  templateUrl: './bill-page.component.html',
  styleUrls: ['./bill-page.component.css']
})
export class BillPageComponent implements OnInit {
  bills: any[] = [];
  customerID!: number; 
  isLoading: boolean = true;
  filteredBills: any[] = [];
  filterStatus: string = 'All'; 


  constructor(
    private billPageService: BillPageService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.customerID = +params['customerID']; 
      this.getCustomerBills(this.customerID);
    });
  }

  getCustomerBills(customerID: number): void {
    this.billPageService.getCustomerBills(customerID).subscribe({
      next: (data) => {
        this.bills = data.map(bill => ({
          billId: bill.billId,
          unit_consumption: bill.unitConsumption,
          bill_start_date: bill.billingStartDate, 
          bill_end_date: bill.billingEndDate,
          due_date: bill.billDueDate,
          total_amount: +bill.amountDue,
          status: bill.billStatus
          
        }));
        this.isLoading = false;
        this.filterBills();
      },
      error: (err) => {
        console.error('Error fetching bills:', err);
        this.isLoading = false;
      }
    });
  }

  goToPayment(billId: number): void {
    this.router.navigate(['/payment', this.customerID,billId]);
  }

  filterBills(): void {
    if (this.filterStatus === 'All') {
      this.filteredBills = this.bills;
    } else {
      const statusFilterLower = this.filterStatus.toLowerCase(); 
      this.filteredBills = this.bills.filter(bill => bill.status.toLowerCase() === statusFilterLower);
    }
  }
  
}
