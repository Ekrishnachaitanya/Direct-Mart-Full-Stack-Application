package com.directmart.ecommerce.service;

import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.directmart.ecommerce.dao.CustomerRepository;
import com.directmart.ecommerce.dto.Purchase;
import com.directmart.ecommerce.dto.PurchaseResponse;
import com.directmart.ecommerce.entity.Customer;
import com.directmart.ecommerce.entity.Order;
import com.directmart.ecommerce.entity.OrderItem;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	private CustomerRepository customerRepository;

	@Autowired
	public CheckoutServiceImpl(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {

		Order order = purchase.getOrder();

		String orderTrackingNumber = generateOrderTrackingNumber();

		order.setOrderTrackingNumber(orderTrackingNumber);

		Set<OrderItem> orderItems = purchase.getOrderItems();

		orderItems.forEach(item -> order.add(item));

		order.setBillingAddress(purchase.getBillingAddress());
		order.setShippingAddress(purchase.getShippingAddress());

		Customer customer = purchase.getCustomer();
		
		String theEmail = customer.getEmail();
		
		Customer customerFromDB = customerRepository.findByEmail(theEmail);
		
		if(customerFromDB!=null) {
			customer = customerFromDB;
		}
		
		customer.add(order);

		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			System.out.println("hi");
		}

		// TODO Auto-generated method stub
		return new PurchaseResponse(orderTrackingNumber);
	}

	private String generateOrderTrackingNumber() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

}
