package com.security.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2ClientProperties {

	private String clientId;
	
	private String clientSecret;
	
	private int accessTokenValiditySeconds;
	
	
}
