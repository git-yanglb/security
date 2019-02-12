package com.security.app.social.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.security.core.social.SocialAuthenticationFilterPostProcessor;

@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {

	@Autowired
	private AuthenticationSuccessHandler successHandler;

	@Override
	public void process(SocialAuthenticationFilter filter) {
		filter.setAuthenticationSuccessHandler(successHandler);
	}

}
