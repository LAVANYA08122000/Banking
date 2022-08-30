import { Router } from '@angular/router';
import { TransactionService } from './../services/transaction.service';
import { TransactionDto } from './../models/transaction-dto';
import { Component, OnInit } from '@angular/core';
import { ViewBeneficiaryService } from '../services/view-beneficiary.service';
import { BeneficiaryDetails } from '../models/show-beneficiary';
import { AccountService } from '../services/account.service';
import { AccountSummaryStatus } from '../models/account-summary-status';
import { FundTransferDto } from '../models/fund-transfer-dto';
import { GenerateOtpService } from '../services/generate-otp.service';
import { BnNgIdleService } from 'bn-ng-idle';

import { NgxSpinnerService } from 'ngx-spinner';
@Component({
  selector: 'app-fund-transfer',
  templateUrl: './fund-transfer.component.html',
  styleUrls: ['./fund-transfer.component.css'],
})
export class FundTransferComponent implements OnInit {
  statusMessage: string;
  statusCode: string;
  transaction: FundTransferDto = new FundTransferDto();

  accountSummaryStatus: AccountSummaryStatus = new AccountSummaryStatus();
  beneficiaries: BeneficiaryDetails[];
  constructor(
    private service: ViewBeneficiaryService,
    private transactionService: TransactionService,
    private router: Router,
    private accountService: AccountService,
    private otpService: GenerateOtpService,
    private bnIdle: BnNgIdleService,
    private SpinnerService: NgxSpinnerService
  ) {
    // this.bnIdle.startWatching(1500).subscribe((res) => {
    //   if (res) {
    //     console.log("fund-transfer");
    //     this.router.navigate(['session-expired']);
    //     this.bnIdle.stopTimer();
    //   }
    // });
  }
  message: string;
  error: boolean;
  transactionDetails: string;
  custId: number;
  otp: string;

  ngOnInit(): void {
    this.custId = parseInt(sessionStorage.getItem('custId'));
    this.showBeneficiary();

    this.accountSummaryStatus.accountNumber = parseInt(
      sessionStorage.getItem('accountNumber')
    );
    this.transaction.fromAccount = sessionStorage.getItem('accountNumber');

    // this.accountService.showAccountSummary(this.custId).subscribe(response => {
    //   if (response.statusCode === "SUCCESS") {
    //     this.accountSummaryStatus.accountNumber = response.accountNumber;

    //     sessionStorage.setItem("accountNumber",String(response.accountNumber));
    //     this.transaction.fromAccountNumber=parseInt(sessionStorage.getItem('accountNumber'));
    //   }
    //   else {
    //     this.error = true;
    //     this.message = response.statusMessage;
    //   }
    // })
  }

  getOTP() {
    this.transactionService.getOTP(this.custId).subscribe((data) => {
      if (data.statusCode === 'SUCCESS') {
        // this.statusMessage = data.statusMessage;
        // this.statusCode = data.statusCode;
        // document.getElementById("openModalButton").click();
        // this.onSubmit();
      } else {
        this.statusMessage = data.statusMessage;
        this.statusCode = data.statusCode;
        document.getElementById('openModalButton').click();
      }
    });
  }

  onSubmit() {
    // this.transaction.fromAccountNumber=parseInt(sessionStorage.getItem('accountNumber'));
    this.SpinnerService.show();
    console.log(this.transaction);
    if (this.transaction.otp == this.otp) {
      this.transactionService.transfer(this.transaction).subscribe((data) => {
        if (data.statusCode === 'SUCCESS') {
          this.SpinnerService.hide();
          // this.transactionDetails=data.transactionDetail;
          alert(data.statusMessage);
          // sessionStorage.setItem('transactionId',String(this.transactionDetails.id));
          // sessionStorage.setItem('fromAccountNumber',String(this.transactionDetails.fromAccountNumber));
          // sessionStorage.setItem('toAccountNumber',String(this.transactionDetails.toAccountNumber));
          // // sessionStorage.setItem('remarks',this.transactionDetails.remarks);
          // sessionStorage.setItem('amount',String(this.transactionDetails.amount));
          // sessionStorage.setItem('transactionAmount',String(this.transactionDetails.amount));
          // sessionStorage.setItem('transactionType',this.transactionDetails.transactionType);
          // sessionStorage.setItem('transactionStatus',data.statusCode);
          this.router.navigate(['account-summary']);
        } else {
          this.SpinnerService.hide();
          sessionStorage.setItem('transactionStatus', data.statusCode);
          alert(data.statusMessage);
        }
      });
    } else {
      this.SpinnerService.hide();
      alert('OTP is incorrect');
    }
  }
  showBeneficiary() {
    this.service
      .showBeneficiary(parseInt(sessionStorage.getItem('accountNumber')))
      .subscribe((data) => {
        if (data.statusCode === 'SUCCESS') {
          this.beneficiaries = data.beneficiaryDto;
        }
      });
  }

  onClick($event: any) {
    $event.preventDefault();
    this.SpinnerService.show();
    this.transactionService.getOTP(this.custId).subscribe((data) => {
      if (data.statusCode == 'SUCCESS') {
        this.SpinnerService.hide();
        this.otp = data.statusMessage;
        this.statusMessage = 'OTP has been sent to your registered Email ID';
        document.getElementById('openModalButton').click();
      } else {
        this.SpinnerService.hide();
        this.statusMessage = 'OTP Generation Failed!!Click to try again';
        document.getElementById('openModalButton').click();
      }
    });
  }

  goToAddBeneficiary($event: any) {
    $event.preventDefault();
    this.router.navigate(['add-beneficiary']);
  }
}
