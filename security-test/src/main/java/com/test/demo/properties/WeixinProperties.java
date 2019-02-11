package com.test.demo.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeixinProperties {

	private String providerId = "weixin";

	private String appId;

	private String appSecret;

}
