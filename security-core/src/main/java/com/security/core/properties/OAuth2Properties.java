package com.security.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2Properties {

	private String jwtSigningKey = "isecurity";
	
	private OAuth2ClientProperties[] clients = {};

}
