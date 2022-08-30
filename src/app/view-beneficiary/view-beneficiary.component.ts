import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ViewBeneficiaryService } from './../services/view-beneficiary.service';
import { BeneficiaryDetails } from './../models/show-beneficiary';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';
import { Router } from '@angular/router';
//import { CompileShallowModuleMetadata } from '@angular/compiler';
import { NgxSpinnerService } from 'ngx-spinner';
//declare var jsPDF: any;
@Component({
  selector: 'app-view-beneficiary',
  templateUrl: './view-beneficiary.component.html',
  styleUrls: ['./view-beneficiary.component.css'],
})
export class ViewBeneficiaryComponent implements OnInit {
  beneficiaries: BeneficiaryDetails[];
  accountNumber: number;
  statusMessage: string;

  beneficiaryId: number;

  constructor(
    private service: ViewBeneficiaryService,
    private router: Router,
    private spinnerService: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.accountNumber = parseInt(sessionStorage.getItem('accountNumber'));
    this.showBeneficiary();
  }

  showBeneficiary() {
    this.service.showBeneficiary(this.accountNumber).subscribe((data) => {
      if (data.statusCode === 'SUCCESS') {
        this.beneficiaries = data.beneficiaryDto;
      } else {
        this.statusMessage =
          'No beneficiary exists. Add new beneficiary to enjoy the effortless transfer of money';
        document.getElementById('openModalButton').click();
      }
    });
  }
  public captureScreen() {
    var data = document.getElementById('contentToConvert');
    html2canvas(data).then((canvas) => {
      var imgWidth = 208;
      var pageHeight = 295;
      var imgHeight = (canvas.height * imgWidth) / canvas.width;
      var heightLeft = imgHeight;

      const contentDataURL = canvas.toDataURL('image/png');
      let pdf = new jsPDF('p', 'mm', 'a4'); // A4 size page of PDF
      var position = 0;
      pdf.addImage(contentDataURL, 'PNG', 0, position, imgWidth, imgHeight);
      pdf.save('beneficiary.pdf'); // Generated PDF
    });
  }

  // reloadComponent() {
  //   let currentUrl = this.router.url;
  //       this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  //       this.router.onSameUrlNavigation = 'reload';
  //       this.router.navigate(['/view-beneficiary']);
  //   }

  deleteBeneficiary(beneficiaryId: number) {
    // this.beneficiaryId = beneficiaryId;
    this.service.deleteBeneficiary(beneficiaryId).subscribe( (data) => {
      try {
        this.statusMessage = 'Something went wrong. Please try again later';
      } catch (error) {
        this.statusMessage = 'Beneficiary Deleted Successfully';
        this.showBeneficiary();
      }
      document.getElementById('openModalButton').click();
      this.spinnerService.show();
      this.router.routeReuseStrategy.shouldReuseRoute = function () {
        return false;
      };

    }, (error) => { 
      this.statusMessage = 'Beneficiary deleted';
      document.getElementById('openModalButton').click();
      // this.spinnerService.hide();
      this.router.routeReuseStrategy.shouldReuseRoute = function () {
        return false;
      };
      this.showBeneficiary();
    }, );
  }

  onDeleteClick(beneficiaryId: number) {
    this.beneficiaryId = beneficiaryId;
    document.getElementById('deleteModalButton').click();
  }

  // load(val) {
  //   if (val == this.router.url) {
  //     this.spinnerService.show();
  //     this.router.routeReuseStrategy.shouldReuseRoute = function () {
  //       return false;
  //     };
  //    }
  //   }

  // onClick($event: any) {
  //   this.router.routeReuseStrategy.shouldReuseRoute = function () {
  //     return false;
  //   };
  // }
}
