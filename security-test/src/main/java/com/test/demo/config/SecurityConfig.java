package com.test.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

import com.test.demo.security.handler.FormFailureHandler;
import com.test.demo.security.handler.FormSucessHandler;
import com.test.demo.security.mobile.SmsCodeConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private FormSucessHandler formSucessHandler;
	
	@Autowired
	private FormFailureHandler formFailureHandler;
	
	@Autowired
	private SmsCodeConfig smsCodeConfig;
	
	@Autowired
	private SpringSocialConfigurer socialConfig;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.apply(smsCodeConfig);
		
		http.apply(socialConfig);
		
		http
			.formLogin()
			.loginPage("/login.html")
			.loginProcessingUrl("/login")
			.successHandler(formSucessHandler)
			.failureHandler(formFailureHandler)
		.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
		.and()
			.authorizeRequests()
			.antMatchers("/","/login.html","/index.html","/header.html",
					"/image/**","/js/**","/code/*","/auth/**")
			.permitAll()
			.anyRequest()
			.authenticated();
		
	}

}
