package com.security.detail;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

	/**
	 * 根据社交用户信息默认创建用户并返回用户id
	 */
	@Override
	public String execute(Connection<?> connection) {
		return connection.getDisplayName();
	}

}
