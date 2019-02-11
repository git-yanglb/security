package com.test.demo.social.qq.api;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

	private static String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

	private static String URL_GET_USER_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

	private String clientId;

	private String openId;

	private ObjectMapper mapper = new ObjectMapper();

	public QQImpl(String accessToken, String clientId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.clientId = clientId;

		String url = String.format(URL_GET_USER_OPENID, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);
		log.info("获取用户openId：{}", result);

		// callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
	}

	@Override
	public QQUserInfo getUserInfo() {
		String url = String.format(URL_GET_USER_INFO, clientId, openId);
		String result = getRestTemplate().getForObject(url, String.class);
		log.info("获取用户详细信息：{}", result);

		try {
			QQUserInfo qqUserInfo = mapper.readValue(result, QQUserInfo.class);
			qqUserInfo.setOpenId(openId);
			return qqUserInfo;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
