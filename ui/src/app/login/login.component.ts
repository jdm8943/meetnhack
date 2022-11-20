import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string = "hello";

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUser("V0")
      .subscribe(user => this.username = user.username);
  }
  
}
