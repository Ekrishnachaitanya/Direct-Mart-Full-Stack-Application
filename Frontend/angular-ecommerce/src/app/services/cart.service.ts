import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { CartItem } from '../common/cart-item';

@Injectable({
  providedIn: 'root'
})
export class CartService {


  cartItems: CartItem[] = [];

  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);

  storage: Storage = localStorage;

  constructor() {
    let data = JSON.parse(this.storage.getItem('cartItems'));

    if (data != null) {
      this.cartItems = data;

      this.computeCartTotals();
    }


  }

  addToCart(theCartItem: CartItem) {

    let alreadyExistsInCart: boolean = false;
    let existingCartItem: CartItem = undefined;

    if (this.cartItems.length > 0) {
      existingCartItem = this.cartItems.find(tempCartItem => tempCartItem.id === theCartItem.id);
    }
    alreadyExistsInCart = (existingCartItem != undefined);

    if (alreadyExistsInCart) {
      existingCartItem.quantity++;
    } else {
      this.cartItems.push(theCartItem);
    }

    this.computeCartTotals();

  }
  computeCartTotals() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    for (let currentCartitems of this.cartItems) {
      totalPriceValue += currentCartitems.quantity * currentCartitems.unitPrice;
      totalQuantityValue += currentCartitems.quantity;
    }

    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);


    this.logCartItems(totalPriceValue, totalQuantityValue);

    this.persistCartItems();
  }

  persistCartItems() {
    this.storage.setItem('cartItems', JSON.stringify(this.cartItems));
  }

  logCartItems(totalPriceValue: number, totalQuantityValue: number) {
    console.log(`Content display`);
    for (let currentCartitems of this.cartItems) {
      const subTotalPrice = currentCartitems.quantity * currentCartitems.unitPrice;
      console.log(`Name ${currentCartitems.name} SubTotalPrice ${subTotalPrice}`);
    }

    console.log(`TotalPrice ${totalPriceValue.toFixed(2)} Total Quantity ${totalQuantityValue}`);
  }

  decrementQuantity(theCartItem: CartItem) {
    theCartItem.quantity--;
    console.log(theCartItem.quantity);
    if (theCartItem.quantity == 0) {
      console.log("hi");
      this.remove(theCartItem);
    } else {
      this.computeCartTotals();
    }
  }
  remove(theCartItem: CartItem) {

    const itemIndex = this.cartItems.findIndex(tempCartItem => tempCartItem.id === theCartItem.id);

    console.log(`itemIndex ${itemIndex}`);

    if (itemIndex > -1) {
      this.cartItems.splice(itemIndex, 1);
      this.computeCartTotals();
    }

  }
}
