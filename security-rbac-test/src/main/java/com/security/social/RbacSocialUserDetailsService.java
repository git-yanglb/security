package com.security.social;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 该版本spring social 不提供 SocialUserDetailsService 实现会报错
 * <P>
 *
 */
@Component
public class RbacSocialUserDetailsService implements SocialUserDetailsService{

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		// TODO 
		return null;
	}

}
