import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BeneficiaryStatus } from './../models/show-beneficiary-status';
import { BeneficiaryDetails} from './../models/show-beneficiary';
@Injectable({
  providedIn: 'root'
})
export class ViewBeneficiaryService {


  constructor(private http:HttpClient) { }


  showBeneficiary(accountNumber:number):Observable<BeneficiaryStatus>{
     return this.http.get<BeneficiaryStatus>('http://localhost:9090/api/viewAllBeneficiaries/'+accountNumber);
  }

  deleteBeneficiary(beneficiaryId:any):Observable<string>{
    return this.http.delete<string>('http://localhost:9090/api/deleteBeneficiary/'+beneficiaryId);
  }
}

