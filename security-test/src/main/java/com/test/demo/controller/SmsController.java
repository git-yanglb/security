package com.test.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SmsController {

	public static final String SESSION_KEY = "sms_session_key";

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@GetMapping("/code/sms")
	public void smsCode(String mobile, HttpServletRequest request, HttpServletResponse response) {
		String randomNumeric = RandomStringUtils.randomNumeric(6);
		log.info("向手机{}发送验证码：{}", mobile, randomNumeric);
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, randomNumeric);
	}

}
