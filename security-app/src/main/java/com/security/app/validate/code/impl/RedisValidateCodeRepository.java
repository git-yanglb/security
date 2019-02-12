package com.security.app.validate.code.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.security.core.validate.code.ValidateCode;
import com.security.core.validate.code.ValidateCodeException;
import com.security.core.validate.code.ValidateCodeRepository;
import com.security.core.validate.code.ValidateCodeType;

/**
 * 基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题
 * 
 * @author TiHom
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
		// 设置30分钟的超时时间
		redisTemplate.opsForValue().set(buildKey(request, validateCodeType), code, 30, TimeUnit.MINUTES);
	}

	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
		Object value = redisTemplate.opsForValue().get(buildKey(request, validateCodeType));
		if (value == null) {
			return null;
		}
		return (ValidateCode) value;
	}

	@Override
	public void remove(ServletWebRequest request, ValidateCodeType codeType) {
		redisTemplate.delete(buildKey(request, codeType));
	}

	private String buildKey(ServletWebRequest request, ValidateCodeType type) {
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId)) {
			throw new ValidateCodeException("请在请求头中携带deviceId参数");
		}
		return "code:" + type.toString().toLowerCase() + ":" + deviceId;
	}
}
