import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { OrgEvent } from '../orgEvent';
import { UserService } from '../user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  username!: string;
  points!: number;
  attending!: OrgEvent[];

  constructor(
    public app: AppComponent,
    private userService: UserService,
  ) { }

  ngOnInit(): void {
    this.getUserInfo();
  }

  getUserInfo() {
    this.userService.getUser((this.app.loggedInID || '').toString()).subscribe(user => {
      this.username = user.username;
      this.points = user.currentPoints;
    });
  }

}
