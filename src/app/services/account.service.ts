import { AccountSummaryStatus } from '../models/account-summary-status';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ViewTopTransactions } from '../models/view-top-transactions';
import { ViewTopTransactionsDto } from '../models/view-top-transactionDto';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) { }

  showAccountSummary(accountNumber: number): Observable<AccountSummaryStatus>{
    let url="http://localhost:9090/api/accountSummary/"+accountNumber;
    return this.http.get<AccountSummaryStatus>(url);
  }

  showTopTransactions(accountNumber:number) : Observable<ViewTopTransactionsDto>{
    let url = "http://localhost:9090/api/findTopFiveTransactions/" + accountNumber;
    return this.http.get<ViewTopTransactionsDto>(url);
}
}
