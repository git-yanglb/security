package com.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import com.security.app.social.openid.OpenIdAuthenticationConfig;
import com.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.security.core.properties.SecurityConstants;
import com.security.core.properties.SecurityProperties;
import com.security.core.validate.code.ValidateCodeSecurityConfig;

@Configuration
@EnableResourceServer
public class AuthoritzationResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired(required = false)
	private SpringSocialConfigurer springSocialConfigurer;
	
	@Autowired
	protected AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	@Autowired
	private OpenIdAuthenticationConfig openIdAuthenticationConfig;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http
			.apply(validateCodeSecurityConfig)
		.and()
			.apply(smsCodeAuthenticationSecurityConfig)
		.and()
			.apply(springSocialConfigurer)
		.and()
			.apply(openIdAuthenticationConfig)
		.and()
			.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler)
		.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl(securityProperties.getBrowser().getLoginPage())
			//.deleteCookies("JESSIONID","SESSION")
		.and()
			.authorizeRequests()
			.antMatchers(
					securityProperties.getBrowser().getLoginPage(),
					SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
					SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
					SecurityConstants.DEFAULT_QQ_LOGIN_IMAGE,
					securityProperties.getBrowser().getSingUpUrl(),
					"/user/regist","/session/invalid","/social/signUp"
				).permitAll()
			.anyRequest()
			.authenticated()
		.and()
			.csrf()
			.disable();
		
		if(springSocialConfigurer != null){
			http.apply(springSocialConfigurer);
		}
		
	}
}
