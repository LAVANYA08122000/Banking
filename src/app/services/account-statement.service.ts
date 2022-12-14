import { StatementTransactionDto } from './../models/account-statement';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {AccountStatementStatus} from './../models/account-statement-status';
import { StatementDuration } from '../models/statement-duration';



@Injectable({
  providedIn: 'root'
})
export class AccountStatementService {

  constructor(private http: HttpClient) { }

  fetchStatement(statementDuration:StatementDuration):Observable<AccountStatementStatus>{
    return this.http.post<AccountStatementStatus>('http://localhost:9090/api/accountStatement',statementDuration);
  }
    

  
  
}