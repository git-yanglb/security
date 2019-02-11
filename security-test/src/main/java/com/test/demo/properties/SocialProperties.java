package com.test.demo.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialProperties {

	private QQProperties qq = new QQProperties();
	
	private WeixinProperties weixin =  new WeixinProperties();

}
