package com.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhailiang
 *
 */
@Getter
@Setter
public class ImageCodeProperties extends SmsCodeProperties {
	
	public ImageCodeProperties() {
		setLength(4);
	}
	 
	private int width = 70;
	
	private int height = 25;
	
}
