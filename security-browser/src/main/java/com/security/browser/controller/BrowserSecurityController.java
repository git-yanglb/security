package com.security.browser.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.security.core.properties.SecurityConstants;
import com.security.core.properties.SecurityProperties;
import com.security.core.support.SimpleResponse;
import com.security.core.support.SocialUserInfo;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BrowserSecurityController {

	private RequestCache requestCache = new HttpSessionRequestCache();

	RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	@RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthtication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String loginPage = securityProperties.getBrowser().getLoginPage();

		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String redirectUrl = savedRequest.getRedirectUrl();
			log.info("引发跳转的请求是：" + redirectUrl);
			if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")
					|| StringUtils.endsWithIgnoreCase(redirectUrl, ".htm")
					|| StringUtils.endsWithIgnoreCase(redirectUrl, ".jsp")) {
				redirectStrategy.sendRedirect(request, response, loginPage);
			}
		}

		return new SimpleResponse("请先登录系统。");
	}

	@GetMapping("/social/info")
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		SocialUserInfo userInfo = new SocialUserInfo();
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));

		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickName(connection.getDisplayName());
		userInfo.setHeadimg(connection.getImageUrl());

		return userInfo;
	}
	
	@GetMapping("/session/invalid")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse sesssionInvalid(HttpServletRequest request, HttpServletResponse response){
		String message = "session失效，请重新登录系统";
		return new SimpleResponse(message);
	}

}
