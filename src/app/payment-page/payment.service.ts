import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BillPageService } from '../bill-page/bill-page.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private baseUrl = 'http://localhost:8080/payment';

  constructor(private http: HttpClient, private BillService: BillPageService) {

  }
  sendOtp(customerId: number): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.baseUrl}/send-otp/${customerId}`, {});
  }

  validateOtp(customerId: number, otp: string): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.baseUrl}/validate-otp/${customerId}?otp=${otp}`, {});
  }


  verifyCard(requestBody: any): Observable<any> {
    return this.http.post<string>(`${this.baseUrl}/verify-card`, requestBody);
  }

  getBillDetails(billId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/bills/${billId}`);
  }

  processPayment(paymentRequest: any): Observable<any> {
    //console.log("PaymentRequest",paymentRequest);
    return this.http.post(`${this.baseUrl}/process-payment`, paymentRequest);
    
  }
  
  

}
