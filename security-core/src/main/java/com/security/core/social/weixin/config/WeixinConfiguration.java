package com.security.core.social.weixin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.web.servlet.View;

import com.security.core.properties.SecurityProperties;
import com.security.core.properties.WeixinProperties;
import com.security.core.social.ConnectView;
import com.security.core.social.weixin.connect.WeixinConnectionFactory;

/**
 * 微信登录配置
 * 
 * @author zhailiang
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "isecurity.social.weixin", name = "app-id")
public class WeixinConfiguration extends SocialConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
		connectionFactoryConfigurer.addConnectionFactory(new WeixinConnectionFactory(weixinConfig.getProviderId(),
				weixinConfig.getAppId(), weixinConfig.getAppSecret()));
	}

	@Bean({ "connect/weixinConnect", "connect/weixinConnected" })
	@ConditionalOnMissingBean(name = "weixinConnectedView")
	public View weixinConnectedView() {
		return new ConnectView();
	}

}
