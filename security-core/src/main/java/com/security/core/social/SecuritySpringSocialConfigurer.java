package com.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义回调路径
 * 
 * @author Lenovo
 *
 */
@Getter
@Setter
public class SecuritySpringSocialConfigurer extends SpringSocialConfigurer {

	// 回调路径
	private String filterProcessesUrl;

	private SocialAuthenticationFilterPostProcessor postProcessor;

	public SecuritySpringSocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter) super.postProcess(object);
		socialAuthenticationFilter.setFilterProcessesUrl(filterProcessesUrl);
		if (postProcessor != null) {
			postProcessor.process(socialAuthenticationFilter);
		}
		return super.postProcess(object);
	}

}
