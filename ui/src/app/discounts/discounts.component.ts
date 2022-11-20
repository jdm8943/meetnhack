import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { Discount } from '../discount';
import { DiscountsService } from '../discounts.service';

@Component({
  selector: 'app-discounts',
  templateUrl: './discounts.component.html',
  styleUrls: ['./discounts.component.css']
})
export class DiscountsComponent implements OnInit {
  discounts: Discount[] = [];

  constructor(private discountService: DiscountsService, public app: AppComponent) { }

  ngOnInit(): void {
    this.getDiscounts();
  }

  getDiscounts(): void {
    this.discountService.getDiscounts();
  }

}
