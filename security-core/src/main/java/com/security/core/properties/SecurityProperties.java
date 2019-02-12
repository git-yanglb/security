package com.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "isecurity")
@Getter
@Setter
public class SecurityProperties {

	private BrowserProperties browser = new BrowserProperties();

	private ValidateCodeProperties code = new ValidateCodeProperties();

	private SocialProperties social = new SocialProperties();

	private OAuth2Properties oauth2 = new OAuth2Properties();

}
