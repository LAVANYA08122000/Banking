import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Customer } from './models/customer';
import { Observable } from 'rxjs';
import { Regresponse } from './regresponse';

@Injectable({
  providedIn: 'root'
})
export class RegisterServiceService {

  constructor(private httpClient:HttpClient) { }
  registerCustomer(customer:Customer):Observable<Regresponse>{
    return this.httpClient.
    post<Regresponse>
    ("http://localhost:9090/api/open-acc",customer);
  }
}
