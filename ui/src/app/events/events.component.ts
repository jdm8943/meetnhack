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
  orgID!: number;
  showingDetail = false;

  constructor(private eventService: EventService, public app: AppComponent) { }

  ngOnInit(): void {
    this.getEvents();
    console.log(this.app.loggedInID);
    console.log((this.app.loggedInID || '').toString()[0]);
  }

  getEvents(): void {
    this.eventService.getEvents()
      .subscribe(orgEvents => {
        this.orgEvents = orgEvents;
        console.log(orgEvents);
      });

  }

  showDetail(): void {
    this.showingDetail = !this.showingDetail;
    console.log(this.showingDetail);
  }
}