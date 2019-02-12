package com.security.app.social.openid;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class OpenIdAuthenticationProvider implements AuthenticationProvider {

	private SocialUserDetailsService socialUserDetailsService;

	private UsersConnectionRepository usersConnectionRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		OpenIdAuthenticationToken openIdAuthenticationToken = (OpenIdAuthenticationToken) authentication;

		Set<String> providerUserIds = new HashSet<String>();
		providerUserIds.add((String) openIdAuthenticationToken.getPrincipal());
		Set<String> userIds = usersConnectionRepository
				.findUserIdsConnectedTo(openIdAuthenticationToken.getProviderId(), providerUserIds);

		if (CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
			throw new InternalAuthenticationServiceException("用户信息错误");
		}

		String userId = userIds.iterator().next();

		SocialUserDetails userDetails = socialUserDetailsService.loadUserByUserId(userId);

		if (userDetails == null) {
			throw new InternalAuthenticationServiceException("用户信息错误");
		}

		OpenIdAuthenticationToken token = new OpenIdAuthenticationToken(userDetails, userDetails.getAuthorities());
		token.setDetails(openIdAuthenticationToken.getDetails());

		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public SocialUserDetailsService getSocialUserDetailsService() {
		return socialUserDetailsService;
	}

	public void setSocialUserDetailsService(SocialUserDetailsService socialUserDetailsService) {
		this.socialUserDetailsService = socialUserDetailsService;
	}

	public UsersConnectionRepository getUsersConnectionRepository() {
		return usersConnectionRepository;
	}

	public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
		this.usersConnectionRepository = usersConnectionRepository;
	}

}
