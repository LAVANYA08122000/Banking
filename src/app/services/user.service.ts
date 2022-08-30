import { ChangePassword } from './../models/changePassword';
import { Status } from './../models/status';
import { RegisterIb } from './../models/register-ib';
import { UserLoginStatus } from './../models/user-login-status';
import { UserLogin } from './../models/user-login';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { SetNewPassword } from '../models/set-new-password';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  login(login: UserLogin): Observable<UserLoginStatus>{
    return this.http.post<any>('http://localhost:9090/api/login',login);
  }

  register(register: RegisterIb): Observable<Status>{
    return this.http.post<any>('http://localhost:9090/api/signup',register);
  }

  changePassword(changePassword: ChangePassword): Observable<Status>{
    return this.http.put<any>('http://localhost:9090/api/changePassword',changePassword);
  }

  setNewPassword(setNewPassword:SetNewPassword):Observable<Status>{
    return this.http.post<any>('http://localhost:9090/api/setNewPassword',setNewPassword);
  }
}
