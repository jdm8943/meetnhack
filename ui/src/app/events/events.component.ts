import { Component, OnInit, ApplicationRef } from '@angular/core';
import { AppComponent } from '../app.component';

import { Event } from '../interfaces/event';
import { EventService } from '../services/events.service';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  events: Event[] = [];

  constructor(private eventService: EventService, public app: AppComponent) { }

  ngOnInit(): void {
    this.getEvents();
  }

  getEvents(): void {
    this.eventService.getEvents()
      .subscribe(events => this.events = events);
  }

  add(orgID: string,
    eventName: string,
    description: string,
    points: number): void {

    orgID = orgID.trim();
    eventName = eventName.trim();
    description = description.trim();
    if (!orgID || !eventName || !description || !points) { return; }
    this.eventService.addEvent({ orgID, eventName, description, points } as Event)
      .subscribe(event => {
        this.events.push(event);
      });

  }

  edit(eventID: string, orgID: string, eventName: string, description: string, points: number): void {
    var classType = new String('Event');
    if (!eventID || !orgID || !eventName || !description || !points) { return; }
    this.eventService.updateEvent({ eventID, orgID, eventName, description, points } as Event).subscribe()
  }

  delete(event: Event): void {
    if (confirm("Are you sure you want to delete " + event.eventName + "?")) {
      this.events = this.events.filter(h => h !== event);
      this.eventService.deleteEvent(event.eventID).subscribe();
    }
  }

}