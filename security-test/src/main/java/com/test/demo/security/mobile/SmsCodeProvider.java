package com.test.demo.security.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsCodeProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		SmsCodeToken smsCodeToken = (SmsCodeToken) authentication;
		String principal = smsCodeToken.getPrincipal();

		UserDetails user = userDetailsService.loadUserByUsername(principal);
		if (user == null) {
			throw new InternalAuthenticationServiceException("该用户不存在");
		}

		SmsCodeToken token = new SmsCodeToken(principal, smsCodeToken.getAuthorities());
		token.setDetails(smsCodeToken.getDetails());

		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsCodeToken.class.isAssignableFrom(authentication);
	}

}
