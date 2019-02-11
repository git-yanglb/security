package com.test.demo.social.qq.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.test.demo.social.qq.api.QQ;

public class QQOAuthen2ConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQOAuthen2ConnectionFactory(String providerId, String clientId, String clientSecret) {
		super(providerId, new QQOAuth2ServiceProvider(providerId, new QQOAuth2Template(clientId, clientSecret)),
				new QQOAuth2ApiAdapter());
	}

}
