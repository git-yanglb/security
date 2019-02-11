package com.security.core.social.weixin.api;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {

	private static String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=%s";

	private ObjectMapper mapper = new ObjectMapper();

	public WeixinImpl(String accessToken) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
	}

	@Override
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
		messageConverters.remove(0);
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return messageConverters;
	}

	@Override
	public WeixinUserInfo getUserInfo(String openId) {
		String url = String.format(URL_GET_USER_INFO, openId);
		String result = getRestTemplate().getForObject(url, String.class);
		if (StringUtils.contains(result, "errcode")) {
			return null;
		}
		try {
			WeixinUserInfo userInfo = mapper.readValue(result, WeixinUserInfo.class);
			return userInfo;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
