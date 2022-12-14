import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fund-transfer-status',
  templateUrl: './fund-transfer-status.component.html',
  styleUrls: ['.././app.component.css']
})
export class FundTransferStatusComponent implements OnInit {

  transactionStatus: string;
  transactionId: number;
  fromAccountNumber: number;
  toAccountNumber: number;
  transactionAmount: number;
  transactionType: string;
  remarks: string;

  constructor() { }

  ngOnInit(): void {
    this.transactionStatus = sessionStorage.getItem('transactionStatus');
    if (this.transactionStatus === "SUCCESS") {
      this.transactionId = parseInt(sessionStorage.getItem('transactionId'));
      this.fromAccountNumber = parseInt(sessionStorage.getItem('fromAccountNumber'));
      this.toAccountNumber = parseInt(sessionStorage.getItem('toAccountNumber'));
      this.transactionAmount = parseFloat(sessionStorage.getItem('transactionAmount'));
      this.transactionType = sessionStorage.getItem('transactionType');
      this.remarks = sessionStorage.getItem('remarks');
    }
  }



}
