import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
// import { TransactionHistory } from 'com/BBC/model/TransactionHistory';

@Injectable({
  providedIn: 'root'
})
export class TransactionPageService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getTransactionHistory(customerID: number, billID: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/customers/${customerID}/bills/${billID}/transactions`);
  }

  filterTransactions(transactions: any[], filter: string): any[] {
    const now = new Date();
    let filteredTransactions = [...transactions];

    switch (filter) {
      case '10days':
        const tenDaysAgo = new Date(now.setDate(now.getDate() - 10));
        filteredTransactions = transactions.filter(transaction => new Date(transaction.paymentDate) >= tenDaysAgo);
        break;
      case '1month':
        const oneMonthAgo = new Date(now.setMonth(now.getMonth() - 1));
        filteredTransactions = transactions.filter(transaction => new Date(transaction.paymentDate) >= oneMonthAgo);
        break;
      case '3months':
        const threeMonthsAgo = new Date(now.setMonth(now.getMonth() - 3));
        filteredTransactions = transactions.filter(transaction => new Date(transaction.paymentDate) >= threeMonthsAgo);
        break;
      case 'all':
      default:
        break;
    }

    return filteredTransactions;
  }
}
