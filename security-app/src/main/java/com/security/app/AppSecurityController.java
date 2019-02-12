package com.security.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.security.app.social.AppSignUpUtils;
import com.security.core.support.SocialUserInfo;

@Controller
public class AppSecurityController {

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	@Autowired
	private AppSignUpUtils appSignUpUtils;

	@GetMapping("/social/signUp")
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		SocialUserInfo userInfo = new SocialUserInfo();

		WebRequest webRequest = new ServletWebRequest(request);

		Connection<?> connection = providerSignInUtils.getConnectionFromSession(webRequest);

		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickName(connection.getDisplayName());
		userInfo.setHeadimg(connection.getImageUrl());

		appSignUpUtils.saveConnectionData(webRequest, connection.createData());

		return userInfo;
	}

}
