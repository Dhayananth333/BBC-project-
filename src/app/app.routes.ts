import { Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { BillPageComponent } from './bill-page/bill-page.component';
import { AuthGuard } from './login-page/auth-login.guard';
import { PaymentPageComponent } from './payment-page/payment-page.component';
import { TransactionPageComponent } from './transaction-page/transaction-page.component';
import { AccountPageComponent } from './account-page/account-page.component';

export const routes: Routes = [
    {path : 'login', component : LoginPageComponent},
    {path : '', redirectTo : "/login", pathMatch : 'full'},
    {path : 'account/:customerId', component : AccountPageComponent, canActivate: [AuthGuard]},
    {path : 'bill/:customerID' , component : BillPageComponent,  canActivate: [AuthGuard]},
    { path: 'transaction-history/:customerId', component: TransactionPageComponent, canActivate: [AuthGuard]},
    { path: 'payment/:customerId/:billId', component: PaymentPageComponent, canActivate: [AuthGuard] }


];
