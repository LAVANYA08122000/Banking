import { NgxSpinnerService } from 'ngx-spinner';
import { TrackApplicationService } from './../services/track-application.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-track-application',
  templateUrl: './track-application.component.html',
  styleUrls: ['.././app.component.css']
})
export class TrackApplicationComponent {

  custId: number;
  status: string;
  submitted: boolean;
  statusMessage: string;
  error: boolean;

  constructor(private trackApplicationService: TrackApplicationService, private spinnerService: NgxSpinnerService) { }

  checkApplicationStatus() {
    this.spinnerService.show();
    this.trackApplicationService.getApplicationStatus(this.custId).subscribe(response => {
      console.log(response);
      if (response.statusCode === "SUCCESS") {
        this.spinnerService.hide();
        this.submitted = true;
        this.error = false;
        this.status = response.accountStatus;
      }
      else {
        this.spinnerService.hide();
        this.error = true;
        this.statusMessage = response.statusMessage;
      }
    })

  }
}