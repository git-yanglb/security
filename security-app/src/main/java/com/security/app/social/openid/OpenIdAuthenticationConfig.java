package com.security.app.social.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class OpenIdAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	@Autowired
	private SocialUserDetailsService socialUserDetailsService;

	@Autowired
	private AuthenticationSuccessHandler successHandler;

	@Autowired
	private AuthenticationFailureHandler failureHandler;

	@Override
	public void configure(HttpSecurity http) throws Exception {

		OpenIdAuthenticationFilter filter = new OpenIdAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(successHandler);
		filter.setAuthenticationFailureHandler(failureHandler);
		filter.setAuthenticationManager(http.getSharedObject(ProviderManager.class));

		OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider();
		openIdAuthenticationProvider.setSocialUserDetailsService(socialUserDetailsService);
		openIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

		http.authenticationProvider(openIdAuthenticationProvider).addFilterBefore(filter,
				UsernamePasswordAuthenticationFilter.class);

	}

}
