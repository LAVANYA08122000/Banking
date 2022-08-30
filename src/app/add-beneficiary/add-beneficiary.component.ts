import { Beneficiary } from './../models/beneficiary';
import { Component, OnInit } from '@angular/core';
import{ AddBeneficiaryService} from './../services/add-beneficiary.service'
import { Router } from '@angular/router';
import { BnNgIdleService } from 'bn-ng-idle';

@Component({
  selector: 'app-add-beneficiary',
  templateUrl: './add-beneficiary.component.html',
  styleUrls: ['./add-beneficiary.component.css']
})
export class AddBeneficiaryComponent implements OnInit {

  beneficiary: Beneficiary = new Beneficiary();
  message:string;
  error:boolean;
  statusMessage:string;

  
  constructor(private service:AddBeneficiaryService,private router:Router , private bnIdle: BnNgIdleService ) {
    // this.bnIdle.startWatching(1500).subscribe((res) => {
    //   if(res) {
    //     console.log("addBeneficiary");
    //     this.router.navigate(['session-expired']);
    //     this.bnIdle.stopTimer();
    //   }
    // })
   }

  ngOnInit() {
   
  }

  showDetails() {
    this.beneficiary.customerAccountNumber=parseInt(sessionStorage.getItem('accountNumber'));
    this.service.addBeneficiary(this.beneficiary).subscribe(data=>{
      if(data.statusCode==="SUCCESS"){
        this.statusMessage=data.statusMessage;
        document.getElementById("openModalButton").click();
        
      }
      else{
        this.error=true;
        this.statusMessage=data.statusMessage;
        document.getElementById("openModalButton").click();
      }
    })
    
  }

  onClick($event:any){
    this.router.navigate(['dashboard']);  
  }

  
}
