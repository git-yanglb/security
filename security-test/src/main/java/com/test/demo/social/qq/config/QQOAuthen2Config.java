package com.test.demo.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

import com.test.demo.properties.QQProperties;
import com.test.demo.properties.SecurityProperties;
import com.test.demo.social.qq.connect.QQOAuthen2ConnectionFactory;

@Configuration
@ConditionalOnProperty(prefix = "isecurity.social.qq", name = { "app-id", "app-secret" })
public class QQOAuthen2Config extends SocialConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		QQProperties qq = securityProperties.getSocial().getQq();
		connectionFactoryConfigurer.addConnectionFactory(
				new QQOAuthen2ConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret()));
	}

}
