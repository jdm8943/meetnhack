import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';
import { EventsService } from '../events.service';
import { OrgEvent } from '../orgEvent';
import { Location } from '@angular/common';
import { UserService } from '../user.service';


@Component({
  selector: 'app-eventdetail',
  templateUrl: './eventdetail.component.html',
  styleUrls: ['./eventdetail.component.css']
})
export class EventDetailComponent implements OnInit {
  orgEvent: OrgEvent | undefined;
  eventID!: number;
  UID!: string;

  constructor(
    private route: ActivatedRoute,
    private eventService: EventsService,
    private userService: UserService,
    private location: Location,
    public app: AppComponent
  ) { }

  ngOnInit(): void {
    this.app.loggedIn();
    this.getOrgEvent();
    this.UID = (this.app.loggedInID || '').toString()
  }

  getOrgEvent(): void {
    const eventid = parseInt(this.route.snapshot.paramMap.get('id')!, 10) || -1;
    this.eventID = eventid;
    console.log(eventid)
    this.eventService.getEvent(this.eventID)
      .subscribe((orgEvent: OrgEvent | undefined) => {
        this.orgEvent = orgEvent;
      });
  }

  goBack(): void {
    this.location.back();
  }

  join(): void {
    console.log("USER SERVICE: ", this.userService.joinEvent(this.UID, this.eventID).subscribe());
  }

  complete(): void {
    console.log("USER SERVIEC: ", this.userService.completeEvent(this.UID, this.eventID).subscribe());
  }

  save(): void {
    if (this.orgEvent) {
      this.eventService.updateEvent(this.orgEvent)
        .subscribe();
    }
  }

}