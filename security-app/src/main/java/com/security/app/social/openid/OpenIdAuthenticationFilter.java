package com.security.app.social.openid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String PARAM_OPENID_KEY = "openId";
	public static final String PARAM_PROVIDERID_KEY = "providerId";

	private String openIdParameter = PARAM_OPENID_KEY;
	private String providerIdParameter = PARAM_PROVIDERID_KEY;
	private boolean postOnly = true;

	public OpenIdAuthenticationFilter() {
		super(new AntPathRequestMatcher("/authentication/openid", "POST"));
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String openId = obtainOpenId(request);

		if (openId == null) {
			openId = "";
		}

		openId = openId.trim();

		String providerId = obtainProviderId(request);

		if (providerId == null) {
			providerId = "";
		}

		providerId = providerId.trim();

		OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openId, providerId);

		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(openIdParameter);
	}

	protected String obtainOpenId(HttpServletRequest request) {
		return request.getParameter(openIdParameter);
	}

	protected String obtainProviderId(HttpServletRequest request) {
		return request.getParameter(providerIdParameter);
	}

	protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	public void setOpenIdParameter(String openIdParameter) {
		Assert.hasText(openIdParameter, "Username parameter must not be empty or null");
		this.openIdParameter = openIdParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getOpenIdParameter() {
		return openIdParameter;
	}

}
