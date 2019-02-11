package com.test.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "isecurity")
@Getter
@Setter
public class SecurityProperties {

	private SocialProperties social = new SocialProperties();

}
