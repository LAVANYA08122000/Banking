import { TransactionDto } from './../models/transaction-dto';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Status } from '../models/status';
import {TransactionSuccessfulDto} from '../models/transaction-status'
import { FundTransferDto } from '../models/fund-transfer-dto';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient) { }

  transfer(transactionDto: FundTransferDto): Observable<TransactionSuccessfulDto>{
    return this.http.post<TransactionSuccessfulDto>('http://localhost:9090/api/fundTransfer',transactionDto);
  }

  getOTP(custId: number): Observable<Status>{
    return this.http.get<Status>('http://localhost:9090/api/getOTP/'+custId);
  }
}
