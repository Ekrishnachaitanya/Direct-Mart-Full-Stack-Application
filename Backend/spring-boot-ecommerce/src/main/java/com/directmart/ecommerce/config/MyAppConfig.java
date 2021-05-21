package com.directmart.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyAppConfig implements WebMvcConfigurer {

	@Value("${endpoints.cors.allowed-origins}")
	private String[] theAllowedOrigins;

	@Value("${spring.data.rest.base-path}")
	private String basePath;

	@Override
	public void addCorsMappings(CorsRegistry cors) {
		// TODO Auto-generated method stub
		cors.addMapping(basePath + "/**").allowedOrigins(theAllowedOrigins);
	}
}
