package com.security.core.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.security.core.validate.code.ValidateCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageCode extends ValidateCode {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;

	public ImageCode(BufferedImage image, String code, Long expireIn) {
		super(code, expireIn);
		this.image = image;
	}

	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super(code, expireTime);
		this.image = image;
	}

}
