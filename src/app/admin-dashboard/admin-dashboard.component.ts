import { AdminSearchCustomerStatus } from './../models/admin-search-customer-status';
import { Router } from '@angular/router';
import { AdminApproval } from './../models/admin-approval';
import { AdminService } from './../services/admin.service';
import { Component, OnInit } from '@angular/core';
import { CustomerRequestStatus } from '../models/customer-request-status';
import { BnNgIdleService } from 'bn-ng-idle';
import { AngularFileViewerModule } from '@taldor-ltd/angular-file-viewer';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css', '.././app.component.css']
})
export class AdminDashboardComponent implements OnInit {

  name: string;
  message: string;
  error: boolean;
  approvalStatus: boolean;
  approvalMessage: string;
  customerRequestStatus: CustomerRequestStatus = new CustomerRequestStatus();
  adminApproval: AdminApproval = new AdminApproval();
  custId: number;
  searchError: boolean;
  generatedCustomer: AdminSearchCustomerStatus = new AdminSearchCustomerStatus();
  wrongServicerefNo: boolean;
  searched: boolean;
  requestsToggle: boolean;
  showApprovalToggle: boolean;
  showSearchToggle: boolean;
  showErrorLogins: boolean;
  pdfSource: string;
  errorLogins: string[] = [];
  blockedUserId: string;

  constructor(private adminService: AdminService, private router: Router, private bnIdle: BnNgIdleService) {
    // this.bnIdle.startWatching(1500).subscribe((res) => {
    //   if(res) {
    //     console.log("admin Dashboard");
    //     this.router.navigate(['session-expired']);
    //     this.bnIdle.stopTimer();
    //   }
    // })
  }

  ngOnInit(): void {
    this.name = sessionStorage.getItem('adminName');
  }

  showPendingRequests() {
    this.requestsToggle = !this.requestsToggle;
    this.adminService.showPendingRequests().subscribe(response => {
      console.log(response);
      if (response.statusCode === "SUCCESS")
        this.customerRequestStatus.customers = response.customers;
      else {
        this.error = true;
        this.message = response.statusMessage;
      }
    })
  }

  showErrorLoginsForm() {
    this.showErrorLogins = !this.showErrorLogins;
    this.adminService.showErrorLogins().subscribe(response => {
      console.log(response);
      if (response.statusCode === "SUCCESS")
        this.errorLogins = response.errorLogins;
      else {
        this.error = true;
        this.message = response.statusMessage;
      }
    })
  }

  unblock() {
  console.log(this.blockedUserId);
    this.adminService.unblock(this.blockedUserId).subscribe(response => {
      if (response.statusCode === "SUCCESS") {
        alert("User Unblocked Successfully");
        this.showErrorLogins = !this.showErrorLogins;
      }
      else {
        alert("Error in Unblocking User");
      }
    })
  }

  showApprovalForm() {
    this.showApprovalToggle = !this.showApprovalToggle;
    this.approvalStatus = false;
  }

  showSearchForm() {
    this.showSearchToggle = !this.showSearchToggle;
  }

  approve() {
    this.adminService.approve(this.adminApproval).subscribe(response => {
      if (response.statusCode === "SUCCESS") {
        this.approvalStatus = true;
        this.approvalMessage = response.statusMessage;
      }
      else {
        this.approvalStatus = true;
        this.approvalMessage = response.statusMessage;
      }
    })
  }

  search() {
    this.adminService.searchCustomerById(this.custId).subscribe(response => {
      console.log
      if (response.statusCode === "SUCCESS") {
        this.generatedCustomer.customer = response.customer;
        if (this.generatedCustomer.customer === null)
          this.wrongServicerefNo = true;
        else
          this.searched = true;
      }
      else
        this.searchError = true;
    })
  }

  public handleClickAadhar(aadharFile: string) {
    this.pdfSource = "assets/uploads/" + aadharFile;
    document.getElementById('openModalButton').click();
    //this.id = item.UserId;
    // this.router.navigate(['document']);
    // console.log("incitive method")
  }

  public handleClickPan(panFile: string) {
    this.pdfSource = "assets/uploads/" + panFile;
    console.log(this.pdfSource);
    document.getElementById('openModalButton').click();

  }

}
