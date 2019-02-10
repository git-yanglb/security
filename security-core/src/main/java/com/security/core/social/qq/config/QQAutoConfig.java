package com.security.core.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

import com.security.core.properties.QQProperties;
import com.security.core.properties.SecurityProperties;
import com.security.core.social.qq.connect.QQConnectionFactory;

@Configuration
@ConditionalOnProperty(prefix = "isecurity.social.qq", name = { "app-id", "app-secret" })
public class QQAutoConfig extends SocialConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		QQProperties qq = securityProperties.getSocial().getQq();
		connectionFactoryConfigurer
				.addConnectionFactory(new QQConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret()));
	}

}
