import { UserProfileStatus } from '../models/user-profile-status';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Customer } from '../models/customer';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {

  constructor(private http: HttpClient) { }

  showUserProfile(custId: number): Observable<UserProfileStatus>{
    let url="http://localhost:9090/api/searchCustomer/"+custId;
    return this.http.get<UserProfileStatus>(url);
  }

  updateProfile(newAccount: Customer): Observable<UserProfileStatus>{
    let url="http://localhost:9090/api/updateProfile";
    return this.http.put<UserProfileStatus>(url, newAccount);
  }
}
