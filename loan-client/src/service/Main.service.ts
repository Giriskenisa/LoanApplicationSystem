import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Loan } from 'src/model/Loan';

@Injectable({
  providedIn: 'root'
})
export class MainService {
  private baseUrl = environment.baseUrl;

  constructor( private http: HttpClient) {

   }
   postForm( loan:Loan):Observable<any> {
    var headers = new HttpHeaders();
    headers.append('Content-Type', 'application/form-data');
    return this.http.post(this.baseUrl +"loan/create-application", loan, {headers: headers })
  }
}
