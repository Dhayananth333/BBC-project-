import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BillPageService {
  private apiUrl = 'http://localhost:8080';
  constructor(private http: HttpClient) {}

  getCustomerBills(customerID : number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/customers/${customerID}/bills`)
  }
}
