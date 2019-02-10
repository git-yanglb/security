package com.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhailiang
 *
 */
@Getter
@Setter
public class SmsCodeProperties {
	
	private int length = 6;
	
	private long expireIn = 120;
	
	private String url;

}
