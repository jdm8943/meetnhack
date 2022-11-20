import { Component } from '@angular/core';

import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public loggedInID: string | null = null;

  title = 'Community Service Game';

  constructor(
    private userService: UserService
  ) { }

  ngOnInit() {
    this.loggedIn();
    console.log((this.loggedInID || '').toString());
  }

  loggedIn(): void {
    this.loggedInID = localStorage.getItem('loginSessId');
  }

  logout(): void {
    localStorage.removeItem('loginSessId');
    this.loggedIn();
  }
}
