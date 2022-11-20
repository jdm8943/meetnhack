import { Component } from '@angular/core';

import {MenubarModule} from 'primeng/menubar';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  loggedInID: string | null = null;
  isAdmin: string | null = null;

  title = 'Community Service Game';
  items: MenuItem[]=[];

  ngOnInit() {
      this.items = [
          {
              label: 'Login',
              url: 'http://localhost:4200/login'
          },
          {
              label: 'Events',
              url: 'http://localhost:4200/events'
          }
      ];
  }

  loggedIn(): void{
    this.loggedInID = localStorage.getItem('loginSessId');
    this.isAdmin = localStorage.getItem('isAdmin');
  }

  logout():void{
    localStorage.removeItem('loginSessId');
    localStorage.removeItem('isAdmin');
    this.loggedIn();
  }
}
