import { UserLogin } from './../models/user-login';
import { UserService } from './../services/user.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserProfileStatus } from '../models/user-profile-status';
import { UserProfileService } from '../services/user-profile.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  userLogin: UserLogin = new UserLogin();
  message: string;
  error: boolean;
  isLoginFailed: boolean = false;

  constructor(
    private userService: UserService,
    private router: Router,
    private SpinnerService: NgxSpinnerService,
    private userProfileService: UserProfileService
  ) {}

  fetchProfile() {}

  loginCheck() {
    this.SpinnerService.show();
    // this.router.navigate(['account-summary']);
    console.log(this.userLogin);
    this.userService.login(this.userLogin).subscribe(
      (data) => {
        if (data.statusCode === 'SUCCESS') {
          sessionStorage.setItem('userId', String(data.userId));
          sessionStorage.setItem('accountNumber', data.accountNumber);
          sessionStorage.setItem('custId', String(data.custId));

          this.userProfileService
            .showUserProfile(data.custId)
            .subscribe((response) => {
              if (response.statusCode === 'SUCCESS') {
                sessionStorage.setItem('name', response.customer.name);
              } else {
                this.error = true;
                this.message = response.statusMessage;
              }
            });
          this.SpinnerService.hide();
          this.router.navigate(['account-summary']);
        } else {
          this.SpinnerService.hide();
          this.error = true;
          this.message = data.statusMessage;
            this.isLoginFailed = true;
        }
      },
      (error) => {
        this.SpinnerService.hide();
        this.error = true;
        this.message = error.error.statusMessage;
      }
    );
  }
}
