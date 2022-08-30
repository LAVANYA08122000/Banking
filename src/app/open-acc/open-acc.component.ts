import { CustomerService } from './../services/customer.service';
import { Customer } from './../models/customer';
import { Component, OnInit } from '@angular/core';
import { Address } from '../models/address';
import { Router } from '@angular/router';
import { AccountStatus } from '../models/account-status';
import { AccountType } from '../models/account-type';

declare var $: any;
@Component({
  selector: 'app-open-acc',
  templateUrl: './open-acc.component.html',
  styleUrls: ['./open-acc.component.css'],
})
export class OpenAccComponent {
  customer: Customer;
  aadharFile: any;
  panFile: any;
  newAccount: Customer = new Customer();
  statusMessage: string;
  statusCode: string;

  aadharUploaded: boolean;
  panUploaded: boolean;

  constructor(
    private customerService: CustomerService,
    private router: Router
  ) {}
  ngOnInit() {
    this.newAccount.accountStatus = AccountStatus.Pending;
    // this.newAccount.accountType= AccountType.Savings;
    this.newAccount.balance = 20000;
  }

  onSubmit() {
    if (!this.aadharUploaded) {
      alert('Please upload aadhar card');
    } else if (!this.panUploaded) {
      alert('Please upload pan card');
    } else {
      this.customerService.openAcc(this.newAccount).subscribe((data) => {
        if (data.statusCode === 'SUCCESS') {
          this.statusCode = data.statusCode;
          this.statusMessage =
            'Registration Successful ,Your customer ID is ' + data.custId;
          document.getElementById('openModalButton').click();
        } else {
          this.statusMessage = data.statusMessage;
          document.getElementById('openModalButton').click();
        }
      });
    }
  }
  onClick($event: any) {
    this.router.navigate(['home']);
  }
  onAadharFileChange(event) {
    this.aadharFile = event.target.files[0];
  }
  onPanFileChange(event) {
    this.panFile = event.target.files[0];
  }

  uploadAadhar() {
    let formData: FormData = new FormData();
    formData.append('file', this.aadharFile);
    // formData.append('panPic', this.panPic);
    this.customerService.picUpload(formData).subscribe((response) => {
      console.log(response);
      if (response.statusCode === 'SUCCESS') {
        this.statusMessage = response.statusMessage;
        this.aadharUploaded = true;
        this.newAccount.aadharFileName = response.statusMessage;
        alert("Aadhar card uploaded successfully");
      } else {
        alert(response.statusMessage);
        this.aadharUploaded = false;
      }
    });
  }

  uploadPan() {
    let formData: FormData = new FormData();
    formData.append('file', this.panFile);
    // formData.append('panPic', this.panPic);
    this.customerService.picUpload(formData).subscribe((response) => {
      console.log(response);
      if (response.statusCode === 'SUCCESS') {
        this.statusMessage = response.statusMessage;
        this.panUploaded = true;
        this.newAccount.panFileName = response.statusMessage;
        alert("PAN card uploaded successfully");
      } else {
        alert(response.statusMessage);
        this.panUploaded = false;
      }
    });
  }
}
