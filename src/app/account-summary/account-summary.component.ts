import { AccountSummaryStatus } from './../models/account-summary-status';
import { AccountService } from './../services/account.service';
import { Account } from './../models/account';
import { Component, OnInit } from '@angular/core';
import { UserProfileStatus } from '../models/user-profile-status';
import { BnNgIdleService } from 'bn-ng-idle';
import { Router } from '@angular/router';
import { ViewTopTransactions } from '../models/view-top-transactions';
import { ViewTopTransactionsDto } from '../models/view-top-transactionDto';

@Component({
  selector: 'app-account-summary',
  templateUrl: './account-summary.component.html',
  styleUrls: ['./account-summary.component.css'],
})
export class AccountSummaryComponent implements OnInit {
  accountNumber: number;
  accountSummaryStatus: AccountSummaryStatus = new AccountSummaryStatus();
  // viewTopTransactions: ViewTopTransactions
  message: string;
  error: boolean;
  topTransactions: ViewTopTransactions[];

  constructor(
    private accountService: AccountService,
    private bnIdle: BnNgIdleService,
    private router: Router
  ) {
    // this.bnIdle.startWatching(1500).subscribe((res) => {
    //   if (res) {
    //     console.log("accountSummary");
    //     this.router.navigate(['session-expired']);
    //     this.bnIdle.stopTimer();
    //   }
    // });
  }

  ngOnInit(): void {
    this.accountNumber = parseInt(sessionStorage.getItem('accountNumber'));
    this.fetchAccountSummary();
    this.fetchTopFive();
  }

  fetchAccountSummary() {
    this.accountService
      .showAccountSummary(this.accountNumber)
      .subscribe((response) => {
        // if (response.statusCode === "SUCCESS") {
        this.accountSummaryStatus.accountNumber = response.accountNumber;
        this.accountSummaryStatus.balance = response.balance;
        this.accountSummaryStatus.accountType = response.accountType;
        // this.viewTopTransactions.transactionId = response.transactionId;
        // this.viewTopTransaction.transactionId = response.transactionId;

        // }
        // else {
        //   this.error = true;
        //   this.message = response.statusMessage;
        // }
      });
  }

  fetchTopFive() {
    this.accountService
      .showTopTransactions(this.accountNumber)
      .subscribe((response) => {
        if (response.statusCode === 'SUCCESS') {
          this.topTransactions = response.topTransactions;
        }
      });
  }
}
