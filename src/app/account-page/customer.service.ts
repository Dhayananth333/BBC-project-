import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private baseUrl = 'http://localhost:8080/customer';

  constructor(private http: HttpClient) { }

  getCustomerDetails(customerId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${customerId}`);
  }

}