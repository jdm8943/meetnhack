import { Component, OnInit, ApplicationRef } from '@angular/core';
import { AppComponent } from '../app.component';

import { OrgEvent } from '../orgEvent';
// import { EventsService } from '../events.service';
import { UserService } from '../user.service';
import { Volunteer_User } from '../volunteer_user';

@Component({
  selector: 'app-attending',
  templateUrl: './attending.component.html',
  styleUrls: ['./attending.component.css']
})
export class AttendingComponent implements OnInit {
  orgEvents: OrgEvent[] = [];
  constructor(
    // private eventService: EventService, 
    public app: AppComponent, 
    private userService: UserService) { }

  ngOnInit(): void {
    this.getEvents();
  }

  getEvents(): void { 
    if(this.app.loggedInID){
      this.userService.getUser(this.app.loggedInID)
        .subscribe(user => {this.orgEvents = (user as Volunteer_User).eventsJoined; console.log(this.orgEvents)});
    }

  }

}
