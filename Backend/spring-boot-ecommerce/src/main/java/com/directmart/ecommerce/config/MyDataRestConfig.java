package com.directmart.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.directmart.ecommerce.entity.Country;
import com.directmart.ecommerce.entity.Order;
import com.directmart.ecommerce.entity.Product;
import com.directmart.ecommerce.entity.ProductCategory;
import com.directmart.ecommerce.entity.State;

//@CrossOrigin("http:/localhost:4200")
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	@Value("${endpoints.cors.allowed-origins}")
	private String[] theAllowedOrigins;

	private EntityManager entityManager;

	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

		HttpMethod[] theUnsupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH };

		// disable HTTP methods for Product: PUT, POST, DELETE and PATCH

		disableHttpMethods(Product.class, config, theUnsupportedActions);

		// disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH
		disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

		disableHttpMethods(Country.class, config, theUnsupportedActions);

		disableHttpMethods(State.class, config, theUnsupportedActions);
		
		disableHttpMethods(Order.class, config, theUnsupportedActions);

		exposeIds(config);

		for (String s : theAllowedOrigins)
			System.out.println(s);

		System.out.println(config.getBasePath());

		cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);

	}

	private void disableHttpMethods(Class<?> theClass, RepositoryRestConfiguration config,
			HttpMethod[] theUnsupportedActions) {
		config.getExposureConfiguration().forDomainType(theClass)
				.withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
	}

	private void exposeIds(RepositoryRestConfiguration config) {

		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

		List<Class<?>> entityClasses = new ArrayList<>();

		for (EntityType<?> temp : entities) {
			entityClasses.add(temp.getJavaType());
		}

		Class<?>[] domainTypes = entityClasses.toArray(new Class[0]);

		config.exposeIdsFor(domainTypes);
	}
}
