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

  constructor(
    private primengConfig: PrimeNGConfig,
    private eventService: EventService,
    private route: ActivatedRoute,
    private location: Location,
    public app: AppComponent
    ) { }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    // this.app.loggedIn();
  }

  add(name: string, description: string, points: number, date: string): void{
    if (!name || !description || !points || !date) {return;}
    this.eventService.addEvent({name, description, points, date} as OrgEvent)
  }

  goBack(): void {
    this.location.back();
  }

}
