package com.security.core.social.qq.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

	private static String GET_USER_INFO_URL = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%sopenid=%s";

	private static String GET_OPENID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";

	private String appId;

	private String openId;

	private ObjectMapper mapper = new ObjectMapper();

	public QQImpl(String accessToken, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appId = appId;

		String url = String.format(GET_OPENID_URL, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);
		log.info("获取用户openid：" + result);

		// callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
	}

	@Override
	public QQUserInfo getUserInfo() {
		String url = String.format(GET_USER_INFO_URL, appId, openId);
		String result = getRestTemplate().getForObject(url, String.class);
		log.info("获取用户信息：" + result);

		try {
			QQUserInfo userInfo = mapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
			return userInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
