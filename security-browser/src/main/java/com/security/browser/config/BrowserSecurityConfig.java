package com.security.browser.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import com.security.browser.session.ExpiredSessionStrategy;
import com.security.core.authentication.AbstractChannelSecurityConfig;
import com.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.security.core.properties.SecurityConstants;
import com.security.core.properties.SecurityProperties;
import com.security.core.validate.code.ValidateCodeSecurityConfig;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired(required = false)
	private SpringSocialConfigurer springSocialConfigurer;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		applyPasswordAuthenticationConfig(http);
		
		if(springSocialConfigurer != null){
			http.apply(springSocialConfigurer);
		}
		
		http.apply(validateCodeSecurityConfig)
			.and()
				.apply(smsCodeAuthenticationSecurityConfig)
			.and()
				.rememberMe()
				.tokenRepository(tokenResponsitory())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService)
			.and()
				.sessionManagement()
				.invalidSessionUrl("/session/invalid")
				.maximumSessions(1)
				//.maxSessionsPreventsLogin(true) // 并发登录达到最大数量时阻止后续登录请求
				.expiredSessionStrategy(new ExpiredSessionStrategy())
				.and()
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
						"/user/regist","/session/invalid"
					).permitAll()
				.anyRequest().authenticated()
			.and()
				.csrf().disable();
		
	}
	
	@Bean
	public PersistentTokenRepository tokenResponsitory(){
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}
	
	
}
