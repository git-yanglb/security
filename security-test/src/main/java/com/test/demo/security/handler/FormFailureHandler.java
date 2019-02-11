package com.test.demo.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.support.SimpleResponse;

@Component
public class FormFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String redirectUrl = savedRequest.getRedirectUrl();
		if (StringUtils.isNoneBlank(redirectUrl) && StringUtils.endsWith(redirectUrl, ".html")) {
			super.onAuthenticationFailure(request, response, exception);
		} else {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(mapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
		}
	}

}
