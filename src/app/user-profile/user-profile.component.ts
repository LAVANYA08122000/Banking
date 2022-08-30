import { UserProfileStatus } from './../models/user-profile-status';
import { UserProfileService } from '../services/user-profile.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from '../models/customer';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
})
export class UserProfileComponent implements OnInit {
  custId: number;
  userProfileStatus: UserProfileStatus = new UserProfileStatus();
  message: string;
  error: boolean;
  newAccount: Customer = new Customer();

  constructor(
    private router: Router,
    private userProfileService: UserProfileService
  ) {}

  ngOnInit(): void {
    this.custId = parseInt(sessionStorage.getItem('custId'));
    this.fetchProfile();
  }

  fetchProfile() {
    this.userProfileService
    .showUserProfile(this.custId)
    .subscribe((response) => {
      console.log('hello');
      console.log(response);
      if (response.statusCode === 'SUCCESS') {

        sessionStorage.setItem('name', response.customer.name);
        this.userProfileStatus.customer = response.customer;
        this.newAccount = response.customer;
        
      } else {
        this.error = true;
        this.message = response.statusMessage;
      }
    });
  }
  

  openModal() {
    document.getElementById('openModalButton').click();
  }

  updateProfile() {

    console.log(this.newAccount);
    console.log(this.userProfileStatus.customer);

    // this.userProfileService.updateProfile(this.newAccount).subscribe(
    //   (response) => {
    //     if (response.statusCode === 'SUCCESS') {
    //       sessionStorage.setItem('name', response.customer.name);
    //       this.message = response.statusMessage;
    //     }
    //     else {
    //       this.error = true;
    //       this.message = response.statusMessage
    //     }});
    // document.getElementById('close-button').click();
  }

  onSubmit() {
    // document.getElementById("openModalButton").click();
    // this.customerService.openAcc(this.newAccount).subscribe(data => {
    //   if(data.statusCode === "SUCCESS"){
    //     this.statusCode=data.statusCode;
    //     this.statusMessage ="Registration Successful ,Service Reference Number is "+data.serviceRefNo;
    //     document.getElementById("openModalButton").click();
    //   }
    //   else{
    //     this.statusMessage=data.statusMessage;
    //     document.getElementById("openModalButton").click();
    //   }
    // })
  }
}
