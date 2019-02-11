package com.test.demo.social.qq.connect;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QQOAuth2Template extends OAuth2Template {

	private static String authorizeUrl = "https://graph.qq.com/oauth2.0/authorize";
	
	private static String accessTokenUrl = "https://graph.qq.com/oauth2.0/token";
	
	public QQOAuth2Template(String clientId, String clientSecret) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}

	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate template = super.createRestTemplate();
		template.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return template;
	}

	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		String result = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		log.info("获取access_token：{}", result);

		// access_token=*****&expires_in=7776000&refresh_token=*****
		String[] allTokens = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");

		String accessToken = StringUtils.substringAfter(allTokens[0], "=");
		Long expiresIn = Long.valueOf(StringUtils.substringAfter(allTokens[1], "="));
		String refreshToken = StringUtils.substringAfter(allTokens[2], "=");

		return new AccessGrant(accessToken, null, refreshToken, expiresIn);
	}
}
