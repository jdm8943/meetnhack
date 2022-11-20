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
  public username!: string

  title = 'Community Service Game';

  constructor(
    private userService: UserService
  ) { }

  ngOnInit() {
    this.loggedIn();
    console.log((this.loggedInID || '').toString());
    this.userService.getUser((this.loggedInID || '').toString()).subscribe(user => {
      this.username = user.username
    })
  }

  loggedIn(): void {
    this.loggedInID = localStorage.getItem('loginSessId');
  }

  logout(): void {
    localStorage.removeItem('loginSessId');
    this.username = ''
    this.loggedIn();
  }
}
