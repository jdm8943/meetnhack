import { Component, OnInit, ApplicationRef } from '@angular/core';
import { AppComponent } from '../app.component';

import { OrgEvent } from '../orgEvent';
import { EventService } from '../events.service';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  orgEvents: OrgEvent[] = [];
  showingDetail = false;
  thisOrgID: Number | null = null;

  constructor(private eventService: EventService, public app: AppComponent) { }

  ngOnInit(): void {
    this.getEvents();
    if(this.app.loggedInID && this.app.loggedInID[0]==='O'){
      this.thisOrgID= Number(this.app.loggedInID.slice(1));
    }
  }

  getEvents(): void {
    this.eventService.getEvents()
      .subscribe(orgEvents => { this.orgEvents = orgEvents; console.log(orgEvents) });
  }

  showDetail(): void {
    this.showingDetail = !this.showingDetail;
    console.log(this.showingDetail);
  }
}