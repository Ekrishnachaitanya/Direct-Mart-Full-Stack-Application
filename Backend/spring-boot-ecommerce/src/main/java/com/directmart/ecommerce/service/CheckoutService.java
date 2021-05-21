package com.directmart.ecommerce.service;

import com.directmart.ecommerce.dto.Purchase;
import com.directmart.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
	PurchaseResponse placeOrder(Purchase purchase);
}
