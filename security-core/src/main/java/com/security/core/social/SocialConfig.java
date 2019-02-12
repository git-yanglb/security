package com.security.core.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import com.security.core.properties.SecurityProperties;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;

	@Autowired(required = false)
	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository connectionRepository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		/*
		 * 设置社交登录与本系统用户关联表前缀
		 */
		// connectionRepository.setTablePrefix("tsm_");
		connectionRepository.setConnectionSignUp(connectionSignUp);
		return connectionRepository;
	}

	@Override
	public UserIdSource getUserIdSource() {

		return new UserIdSource() {
			@Override
			public String getUserId() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null) {
					throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
				}
				return authentication.getName();
			}
		};

	}

	@Bean
	public SpringSocialConfigurer springSocialConfigurer() {
		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
		SecuritySpringSocialConfigurer socialConfigurer = new SecuritySpringSocialConfigurer(filterProcessesUrl);
		socialConfigurer.signupUrl(securityProperties.getBrowser().getSingUpUrl());
		if (socialAuthenticationFilterPostProcessor != null) {
			socialConfigurer.setPostProcessor(socialAuthenticationFilterPostProcessor);
		}
		return socialConfigurer;
	}

	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator,
				getUsersConnectionRepository(connectionFactoryLocator));
	}

}
