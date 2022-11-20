import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EventsComponent } from './events/events.component';

import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { ButtonModule } from 'primeng/button';

import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { InputTextModule } from 'primeng/inputtext';
import { CheckboxModule } from 'primeng/checkbox';
import { ToolbarModule } from 'primeng/toolbar';
import { CardModule } from 'primeng/card';


import { AddEventComponent } from './add-event/add-event.component';
import { RippleModule } from 'primeng/ripple';
import { EventDetailComponent } from './eventdetail/eventdetail.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { DiscountsComponent } from './discounts/discounts.component';
import { AttendingComponent } from './attending/attending.component';
import { ProfileComponent } from './profile/profile.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    EventsComponent,
    AddEventComponent,
    EventDetailComponent,
    DiscountsComponent,
    AttendingComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ButtonModule,
    MenubarModule,
    InputTextModule,
    CheckboxModule,
    ToolbarModule,
    RippleModule,
    NoopAnimationsModule,
    CardModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

  ngOnInit() {
  }
}
