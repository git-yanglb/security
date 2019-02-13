package com.security.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrowserProperties {

	/**
	 * 注册页面
	 */
	private String loginPage = "/signin.html";

	/**
	 * 登录类型，分为JOSN,REDIRECT两种，若设置为JSON，则根据请求类型返回认证结果
	 */
	private LoginType loginType = LoginType.JSON;

	/**
	 * 登录路径
	 */
	private String loginProcessingUrl = "/security/form";

	/**
	 * 记住我时间
	 */
	private int rememberMeSeconds = 36000;

	/**
	 * 注册页
	 */
	private String singUpUrl = "/signup.html";
	
	private String loginSuccessUrl;

}
