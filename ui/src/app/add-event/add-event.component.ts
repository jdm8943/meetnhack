import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';

import { Location } from '@angular/common';
import { PrimeNGConfig } from 'primeng/api';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {

  constructor(
    private primengConfig: PrimeNGConfig,
    private route: ActivatedRoute,
    private location: Location,
    public app: AppComponent
    ) { }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    // this.app.loggedIn();
  }

  add(name: string, description: string, points: number, date: string): void{
    var classType = new String('Event');
    if (!name || !description || !points || !date) {return;}
    
  }

  goBack(): void {
    this.location.back();
  }

}
