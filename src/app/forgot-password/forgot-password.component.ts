import { ForgotPasswordService } from './../services/forgot-password.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['.././app.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  model: any = {};
  userId: number;
  userDoesntExist: boolean;
  error: boolean;
  message: string;
  statusMessage: string;

  constructor(private forgotPasswordService: ForgotPasswordService, private router: Router, private spinnerService: NgxSpinnerService) { }

  ngOnInit(): void {
  }

  redirect(){
    this.router.navigate(['/login']);
  }

  forgotPassword() {
    this.spinnerService.show();
    this.forgotPasswordService.forgotPassword(this.model.userId).subscribe(response => {
      if (response.statusCode === "SUCCESS") {
        // if (response.userExists) {
          sessionStorage.setItem('userId', String(this.model.userId));
          this.spinnerService.hide();
          this.statusMessage = response.statusMessage;
          document.getElementById("openModalButton").click();
        // }
        // else{
        //   this.userDoesntExist = true;
        //   this.spinnerService.hide();
        // }
      }
      else {
        this.error = true;
        this.message = response.statusMessage;
        this.spinnerService.hide();
      }
    })
  }
}
