import { OpenAccStatus } from './../models/open-acc-status';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from '../models/customer';
import { Status } from '../models/status';


@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  openAcc(customer: Customer) :Observable<OpenAccStatus>{
    return this.http.post<any>('http://localhost:9090/api/openAccount',customer);
  }

  picUpload(formData: FormData) : Observable<Status> {
    let url = "http://localhost:9090/api/documentUpload";
    return this.http.post<Status>(url, formData);
  }
  
  getCustomerFile(custId:number):Observable<Customer>{
    return this.http.get<Customer>("http://localhost:9090/admin/profile?custId="+custId);
  }
}
