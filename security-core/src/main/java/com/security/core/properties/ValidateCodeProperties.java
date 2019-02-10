package com.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhailiang
 *
 */
@Getter
@Setter
public class ValidateCodeProperties {

	private ImageCodeProperties image = new ImageCodeProperties();

	private SmsCodeProperties sms = new SmsCodeProperties();

}
