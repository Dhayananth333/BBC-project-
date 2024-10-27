import { Component, OnInit, inject, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TransactionPageService } from './transaction-page.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-transaction-page',
  templateUrl: './transaction-page.component.html',
  standalone: true,
  imports: [CommonModule, FormsModule],
  styleUrls: ['./transaction-page.component.css']
})
export class TransactionPageComponent implements OnInit {
  transactionHistory: any[] = [];
  filteredTransactions: any[] = [];
  customerId!: number;
  billId!: number;
  isLoading : boolean = true;
  selectedFilter: string = 'all';

  @ViewChild('warningModal') warningModal!: TemplateRef<any>; 

  constructor(
    private route: ActivatedRoute,
    private transactionPageService: TransactionPageService,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.customerId = Number(this.route.snapshot.paramMap.get('customerId'));
    this.billId = Number(this.route.snapshot.paramMap.get('billId'));
    console.log('Customer ID in oninit',this.customerId);
    this.loadTransactionHistory();

    this.transactionPageService.getTransactionHistory(this.customerId, this.billId)
      .subscribe({
        next: (data) => {
          this.transactionHistory = data;
          console.log('Transaction :', this.transactionHistory);

          if (this.transactionHistory.length === 0) {
            this.openModal();  
          }
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching transaction history:', err);
          this.openModal();  
          this.isLoading = false;
        }
      });
  }

  openModal() {
    const modalRef = this.modalService.open(this.warningModal);
    modalRef.result.then((result) => {
      console.log(`Modal closed with: ${result}`);
      this.closeModalAndNavigate();
      this.isLoading = false;
    }, (reason) => {
      console.log(`Modal dismissed with: ${reason}`);
      this.closeModalAndNavigate();
      this.isLoading = false;
    });
  }

  closeModalAndNavigate() {
    this.modalService.dismissAll();
    
    this.router.navigate(['/bill', this.customerId]); 
  }

  loadTransactionHistory() {
    this.transactionPageService.getTransactionHistory(this.customerId, this.billId).subscribe(transactions => {
      this.transactionHistory = transactions;
      this.filteredTransactions = transactions;
      this.isLoading = false;
    });
  }

  filterTransactions() {
    this.filteredTransactions = this.transactionPageService.filterTransactions(this.transactionHistory, this.selectedFilter);
  }
}
