import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Loan } from 'src/model/Loan';
import { Response } from 'src/model/LoanResponse';
import { MainService } from 'src/service/Main.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  constructor(private service:MainService,
    private cdf:ChangeDetectorRef) { }


  public loan = new Loan();   
   response = new Response();   
   public check=true;     
   error=false;
   success=false;
   message="";
  ngOnInit(): void {
  }


  public sendRequest(){
    this.sifirla();
    if(!this.check){
      this.errorSetle("Sözleşmeyi Okuyup Kabul Ediniz");
      return;
    }
    this.service.postForm(this.loan).subscribe((result)=>{
     this.response = result;
     if(this.response.status){
      this.success = true;
     }else{
      this.errorSetle(this.response.message);
     }
     
    },error=>{
      this.message = error.error.toString();
      this.error = true;
      this.cdf.detectChanges();
    });

  }

 sifirla(){
  this.error = false;
  this.message = "";
  this.success = false;
 }

 errorSetle(message){
   this.message = message;
   this.error = true;
 }

}
