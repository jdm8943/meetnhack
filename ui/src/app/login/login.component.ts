import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../user';

import { PrimeNGConfig } from 'primeng/api';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string = "hello";
  password: string = "hello";

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private primengConfig: PrimeNGConfig,
    public app: AppComponent,
    private router: Router,
    // public snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    // this.userService.getUser("V0")
    //   .subscribe(user => {this.username = user.username; this.password = user.password});

    this.primengConfig.ripple = true;
  }

  public login(username: String, password: String) {
    username = username.trim();
    password = password.trim();
    var UID: String = "";

    if (!username || !password) {
      confirm("Missing username or password");
      return;
    }

    if (this.app.loggedInID != null) {
      this.app.logout();
    }
    this.userService.login({ UID, username, password } as User).subscribe((response) => {
      localStorage.setItem('loginSessId', String(response.UID));
      this.app.loggedIn();
    },
      (err) => console.log(err)
    );
    // this.snackBar.open('Message archived');
    this.router.navigateByUrl('/events');
    return;
  }

}
