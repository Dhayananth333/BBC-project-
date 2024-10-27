import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private apiUrl = 'http://localhost:8080';
  private loggedIn = false;
  private customerId: number | null = null;

  constructor(private http: HttpClient) {}

  verifyCustomerID(customerID: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/verify/${customerID}`);
  }

  validateOtp(customerID: number, otp: string): Observable<boolean> {
    return this.http.post<boolean>(`${this.apiUrl}/validateOtp/${customerID}`, otp, { responseType: 'json' });
  }

  clearOtp(customerID: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/clearOtp/${customerID}`, {});
  }

  login(customerID: number | null): void { 
    this.loggedIn = true;
    this.customerId = customerID;
    localStorage.setItem('isLoggedIn', 'true');
    localStorage.setItem('customerId', String(customerID));
  }

  logout(): void {
    this.loggedIn = false;
    this.customerId = null;
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('customerId');
  }

  isLoggedIn(): boolean {
    return this.loggedIn || localStorage.getItem('isLoggedIn') === 'true';
  }

  getCustomerId(): number | null {
    return this.customerId || (localStorage.getItem('customerId') ? parseInt(localStorage.getItem('customerId')!, 10) : null);
  }
}
