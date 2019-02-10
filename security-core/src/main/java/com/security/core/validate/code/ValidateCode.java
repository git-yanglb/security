package com.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhailiang
 *
 */
@Getter
@Setter
public class ValidateCode implements Serializable{

	private static final long serialVersionUID = 1L;

	protected String code;

	protected LocalDateTime expireTime;

	public ValidateCode(String code, long expireIn) {
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}

	public ValidateCode(String code, LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}

	public boolean isExpried() {
		return LocalDateTime.now().isAfter(expireTime);
	}

}
