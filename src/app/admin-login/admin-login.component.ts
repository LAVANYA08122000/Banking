import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';
import { AdminService } from './../services/admin.service';
import { AdminLogin } from './../models/admin-login';
import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['.././app.component.css']
})
export class AdminLoginComponent {

  adminLogin: AdminLogin = new AdminLogin();
  message: string;
  error: boolean;

  constructor(private adminService: AdminService, private router: Router, private spinnerService: NgxSpinnerService) { }

  loginCheck() {
    this.spinnerService.show();
    console.log(this.adminLogin);
    this.adminService.login(this.adminLogin).subscribe(response => {
      console.log(response);
      if (response.statusCode === "SUCCESS") {
        sessionStorage.setItem('adminId', String(response.adminId));
        sessionStorage.setItem('adminName', response.name);
        this.spinnerService.hide();  
        this.router.navigate(['admin-dashboard']);
      }
      else {
        this.spinnerService.hide();  
        this.error = true;
        this.message = response.statusMessage;
      }
    })
  }
}
