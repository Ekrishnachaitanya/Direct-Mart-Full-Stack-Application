package com.directmart.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.okta.spring.boot.oauth.Okta;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().antMatchers("/api/orders/**").authenticated().and().oauth2ResourceServer().jwt();

		http.cors();
		
		Okta.configureResourceServer401ResponseBody(http);
		
		http.csrf().disable();
	}

}
