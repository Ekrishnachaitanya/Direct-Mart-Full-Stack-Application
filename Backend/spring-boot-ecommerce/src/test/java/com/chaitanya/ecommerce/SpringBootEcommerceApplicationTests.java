package com.chaitanya.ecommerce;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.directmart.ecommerce.dao.ProductRepository;
import com.directmart.ecommerce.entity.Product;

@SpringBootTest
class SpringBootEcommerceApplicationTests {

	@Autowired
	ProductRepository repo;

	@Test
	void test() {
		Iterable<Product> list = repo.findAll();
		for (Product p : list) {
			System.out.println(p);
		}
	}

}
