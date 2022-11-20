import { Component } from '@angular/core';

import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public loggedInID: string | null = null;

  title = 'Community Service Game';

  ngOnInit() {
    this.loggedIn();
  }

  loggedIn(): void {
    this.loggedInID = localStorage.getItem('loginSessId');
  }

  logout(): void {
    localStorage.removeItem('loginSessId');
    this.loggedIn();
  }
}
