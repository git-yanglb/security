package com.security.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialProperties {

	private QQProperties qq = new QQProperties();
	
	// qq登录回调路径
	private String filterProcessesUrl = "/auth";

}
