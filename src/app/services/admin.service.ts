import { AdminSearchCustomerStatus } from './../models/admin-search-customer-status';
import { Status } from './../models/status';
import { CustomerRequestStatus } from './../models/customer-request-status';
import { AdminStatus } from '../models/admin-status';
import { AdminLogin } from '../models/admin-login';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {ErrorLoginStatus} from '../models/ErrorLoginStatus';
import { AdminApproval } from '../models/admin-approval';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  login(adminLogin: AdminLogin): Observable<AdminStatus>{
    let url = "http://localhost:9090/admin/adminLogin";
    return this.http.post<AdminStatus>(url, adminLogin);
  }

  showPendingRequests(): Observable<CustomerRequestStatus>{
    let url = "http://localhost:9090/admin/pendingRequests";
    return this.http.get<CustomerRequestStatus>(url);
  }

  showErrorLogins(): Observable<ErrorLoginStatus>{
    let url = "http://localhost:9090/api/getErrorLogins";
    return this.http.get<ErrorLoginStatus>(url);
  }

  unblock(userId: string): Observable<Status>{
    return this.http.delete<Status>("http://localhost:9090/api/unblockAccount/"+userId);
  }

  approve(adminApproval: AdminApproval): Observable<Status>{
    let url = "http://localhost:9090/admin/updateRequest";
    return this.http.put<Status>(url, adminApproval);
  }

  searchCustomerById(custId: number): Observable<AdminSearchCustomerStatus>{
    return this.http.get<AdminSearchCustomerStatus>("http://localhost:9090/api/searchCustomer/"+custId);
  }
}
