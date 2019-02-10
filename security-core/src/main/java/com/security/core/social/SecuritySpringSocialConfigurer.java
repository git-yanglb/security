package com.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 自定义回调路径
 * 
 * @author Lenovo
 *
 */
public class SecuritySpringSocialConfigurer extends SpringSocialConfigurer {

	// 回调路径
	private String filterProcessesUrl;

	public SecuritySpringSocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter) super.postProcess(object);
		socialAuthenticationFilter.setFilterProcessesUrl(filterProcessesUrl);
		return super.postProcess(object);
	}

}
