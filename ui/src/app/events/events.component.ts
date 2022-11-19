import { Component, OnInit, ApplicationRef } from '@angular/core';
import { AppComponent } from '../app.component';

import { Product } from '../product';
import { EventService } from '../services/events.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService, public app: AppComponent) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products);
  }

  add(name: string, prodtype: string, quantity: Number, price: Number): void { //need to add other parameters prodtype, quantity, price
    name = name.trim();
    prodtype = prodtype.trim();
    var classType = new String('Product');
    if (!name || !prodtype || !quantity || !price) { return; }
    this.productService.addProduct({ classType, name, prodtype, quantity, price } as Product)
      .subscribe(product => {
        this.products.push(product);
      });

  }

  edit(id: number, name: string, prodtype: string, quantity: Number, price: Number): void {
    var classType = new String('Product');
    if (!id || !name || !prodtype || !quantity || !price) { return; }
    // this.productService.deleteProduct(id)
    // this.productService.addProduct({type, id, name, prodtype, quantity, price} as Product)
    this.productService.updateProduct({ classType, id, name, prodtype, quantity, price } as Product).subscribe()
  }

  delete(product: Product): void {
    if (confirm("Are you sure you want to delete " + product.name + "?")) {
      this.products = this.products.filter(h => h !== product);
      this.productService.deleteProduct(product.id).subscribe();
    }
  }

}