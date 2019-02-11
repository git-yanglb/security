package com.test.demo.social.qq.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;

import com.test.demo.social.qq.api.QQ;
import com.test.demo.social.qq.api.QQImpl;

public class QQOAuth2ServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	private String clientId;

	public QQOAuth2ServiceProvider(String clientId, OAuth2Operations oauth2Operations) {
		super(oauth2Operations);
		this.clientId = clientId;
	}

	@Override
	public QQ getApi(String accessToken) {
		return new QQImpl(accessToken, clientId);
	}

}
