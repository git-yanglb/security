package com.test.demo.security.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.test.demo.security.handler.FormFailureHandler;
import com.test.demo.security.handler.FormSucessHandler;

@Configuration
public class SmsCodeConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private FormSucessHandler formSucessHandler;
	@Autowired
	private FormFailureHandler formFailureHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
		smsCodeFilter.setAuthenticationSuccessHandler(formSucessHandler);
		smsCodeFilter.setAuthenticationFailureHandler(formFailureHandler);
		smsCodeFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

		SmsCodeProvider smsCodeProvider = new SmsCodeProvider();
		smsCodeProvider.setUserDetailsService(userDetailsService);

		http.authenticationProvider(smsCodeProvider).addFilterBefore(smsCodeFilter,
				UsernamePasswordAuthenticationFilter.class);
	}

}
