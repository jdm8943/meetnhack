import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';

import { Location } from '@angular/common';
import { PrimeNGConfig } from 'primeng/api';
import { EventService } from '../events.service';
import { OrgEvent } from '../orgEvent';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {
  orgID!: number

  constructor(
    private primengConfig: PrimeNGConfig,
    private eventService: EventService,
    private route: ActivatedRoute,
    private location: Location,
    public app: AppComponent
  ) { }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.app.loggedIn();
    this.orgID = parseInt((this.app.loggedInID || '').toString().substring(1));
    console.log(this.orgID);
  }

  add(orgID: number, eventName: string, description: string, points: number, date: string): void {
    if (!orgID || !eventName || !description || !points || !date) { return; }
    this.eventService.addEvent({ orgID, eventName, description, points, date } as OrgEvent)
      .subscribe();
    return;
  }

  goBack(): void {
    this.location.back();
  }


  // add(orgID: string,
  //   eventName: string,
  //   description: string,
  //   points: number): void {

  //   orgID = orgID.trim();
  //   eventName = eventName.trim();
  //   description = description.trim();
  //   if (!orgID || !eventName || !description || !points) { return; }
  //   this.eventService.addEvent({ orgID, eventName, description, points } as OrgEvent)
  //     .subscribe(orgEvent => {
  //       this.orgEvents.push(orgEvent);
  //     });

  // }

  // edit(eventID: string, orgID: string, eventName: string, description: string, points: number): void {
  //   var classType = new String('Event');
  //   if (!eventID || !orgID || !eventName || !description || !points) { return; }
  //   this.eventService.updateEvent({ orgID, eventName, description, points } as OrgEvent).subscribe()
  // }

  // delete(orgEvent: OrgEvent): void {
  //   if (confirm("Are you sure you want to delete " + orgEvent.eventName + "?")) {
  //     this.orgEvents = this.orgEvents.filter(h => h !== orgEvent);
  //     this.eventService.deleteEvent(orgEvent.eventID).subscribe();
  //   }
  // }
}
