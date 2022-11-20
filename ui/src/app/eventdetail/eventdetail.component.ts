import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';
import { EventService } from '../events.service';
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
  eventID: Number | undefined;

  constructor(
    private route: ActivatedRoute,
    private eventService: EventService,
    private userService: UserService,
    private location: Location,
    public app: AppComponent
  ) { }

  ngOnInit(): void {
    this.app.loggedIn();
    this.getOrgEvent();
  }

  getOrgEvent(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    console.log(this.route.snapshot.paramMap.get('id'));
    console.log(id);
    this.eventService.getEvent(id)
      .subscribe(orgEvent => {
        this.orgEvent = orgEvent;
      });
  }

  goBack(): void {
    this.location.back();
  }

  join(eventID: number): void{
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    console.log(this.userService.addEvent(String(id), eventID));
  }

  complete(): void{

  }

  save(): void {
    if (this.orgEvent) {
      this.eventService.updateEvent(this.orgEvent)
        .subscribe();
    }
  }

}