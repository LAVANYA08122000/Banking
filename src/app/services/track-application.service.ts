import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TrackApplicationStatus } from '../models/track-application-status';

@Injectable({
  providedIn: 'root'
})
export class TrackApplicationService {

  constructor(private http: HttpClient) { }

  getApplicationStatus(custId: number): Observable<TrackApplicationStatus>{
    let url="http://localhost:9090/api/trackApplication/"+custId;
    return this.http.get<TrackApplicationStatus>(url);
  }
}
