import { ChangePassword } from './../models/changePassword';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  model: any = {};
  statusMessage:string;
  changePassword: ChangePassword= new ChangePassword();
  constructor(private userService: UserService , private router: Router) { }

  ngOnInit(): void {

    
  }
  onSubmit(){
    this.changePassword.userId=parseInt(sessionStorage.getItem('userId'));
    this.changePassword.loginPassword=this.model.loginPassword;
    this.changePassword.transactionPassword=this.model.transactionPassword;
    console.log(this.changePassword);
    this.userService.changePassword(this.changePassword).subscribe(data => {
      console.log(data);
      if(data.statusCode === "SUCCESS"){
        this.statusMessage="Passwords changed successfully";
        document.getElementById("openModalButton").click();
        // this.router.navigate(['account-summary']);
      }
      else{
        this.statusMessage=data.statusMessage;
        document.getElementById("openModalButton").click();
      }
    })
  }

  

}
