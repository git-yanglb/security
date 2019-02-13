package com.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.security.core.properties.SecurityConstants;
import com.security.core.properties.SecurityProperties;

@Component
@Order(Integer.MIN_VALUE)
public class SecurityAuthorizeConfigProvider implements AuthorizeConfigProvider{

	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		registry
			.antMatchers(
					securityProperties.getBrowser().getLoginPage(),
					SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
					SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
					SecurityConstants.DEFAULT_QQ_LOGIN_IMAGE,
					securityProperties.getBrowser().getSingUpUrl(),
					"/user/regist", "/session/invalid", "/social/signUp"
				)
			.permitAll();
	}

}
