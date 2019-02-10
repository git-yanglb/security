package com.security.core.social.qq.connect;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QQOauth2Template extends OAuth2Template {

	public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}

	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}

	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		String result = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		log.info("获取access_token响应：" + result);
		String[] allTokens = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");

		String accessToken = StringUtils.substringAfterLast(allTokens[0], "=");
		Long expireIn = Long.valueOf(StringUtils.substringAfterLast(allTokens[1], "="));
		String refreshToken = StringUtils.substringAfterLast(allTokens[2], "=");

		return new AccessGrant(accessToken, null, refreshToken, expireIn);
	}

}
