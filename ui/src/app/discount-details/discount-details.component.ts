import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';
import { Discount } from '../discount';
import { DiscountsService } from '../discounts.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-discount-details',
  templateUrl: './discount-details.component.html',
  styleUrls: ['./discount-details.component.css']
})
export class DiscountDetailsComponent implements OnInit {
  discount: Discount | null = null;

  constructor(private route: ActivatedRoute,
    public app: AppComponent,
    private discountService: DiscountsService,
    private location: Location,) { }

  ngOnInit(): void {
    this.app.loggedIn();
    
  }

  getDiscount(): void{
    const discountid = parseInt(this.route.snapshot.paramMap.get('id')!, 10) || -1;
    this.discountService.getDiscount(discountid).
      subscribe(disc => {this.discount = disc; console.log("ASF"); console.log(disc);})

  }

  goBack(): void {
    this.location.back();
  }

}
