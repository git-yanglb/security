package com.security.app.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.core.properties.LoginType;
import com.security.core.properties.SecurityProperties;
import com.security.core.support.SimpleResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("登录失败");
		if (securityProperties.getBrowser().getLoginType().equals(LoginType.JSON)) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(mapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
