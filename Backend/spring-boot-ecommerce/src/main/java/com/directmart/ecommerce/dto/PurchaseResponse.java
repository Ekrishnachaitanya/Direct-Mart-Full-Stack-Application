package com.directmart.ecommerce.dto;

import lombok.NonNull;

public class PurchaseResponse {

	@NonNull
	private String orderTrackingNumber;

	public PurchaseResponse(String orderTrackingNumber) {
		super();
		this.orderTrackingNumber = orderTrackingNumber;
	}

	public String getOrderTrackingNumber() {
		return orderTrackingNumber;
	}

	public void setOrderTrackingNumber(String orderTrackingNumber) {
		this.orderTrackingNumber = orderTrackingNumber;
	}

}
