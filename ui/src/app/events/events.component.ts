// import { Component, OnInit, ApplicationRef } from '@angular/core';
// import { AppComponent } from '../app.component';

// import { OrgEvent } from '../orgEvent';
// import { EventService } from '../events.service';

// @Component({
//   selector: 'app-events',
//   templateUrl: './events.component.html',
//   styleUrls: ['./events.component.css']
// })
// export class EventsComponent implements OnInit {
//   orgEvents: OrgEvent[] = [];

//   constructor(private eventService: EventService, public app: AppComponent) { }

//   ngOnInit(): void {
//     this.getEvents();
//   }

//   getEvents(): void {
//     this.eventService.getEvents()
//       .subscribe(orgEvents => this.orgEvents = orgEvents);
//   }

//   add(orgID: string,
//     eventName: string,
//     description: string,
//     points: number): void {

//     orgID = orgID.trim();
//     eventName = eventName.trim();
//     description = description.trim();
//     if (!orgID || !eventName || !description || !points) { return; }
//     this.eventService.addEvent({ orgID, eventName, description, points } as OrgEvent)
//       .subscribe(orgEvent => {
//         this.orgEvents.push(orgEvent);
//       });

//   }

//   edit(eventID: string, orgID: string, eventName: string, description: string, points: number): void {
//     var classType = new String('Event');
//     if (!eventID || !orgID || !eventName || !description || !points) { return; }
//     this.eventService.updateEvent({ orgID, eventName, description, points } as OrgEvent).subscribe()
//   }

//   delete(orgEvent: OrgEvent): void {
//     if (confirm("Are you sure you want to delete " + orgEvent.eventName + "?")) {
//       this.orgEvents = this.orgEvents.filter(h => h !== orgEvent);
//       this.eventService.deleteEvent(orgEvent.eventID).subscribe();
//     }
//   }

// }