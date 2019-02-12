package com.security.app.social;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.social.security.SpringSocialConfigurer;

public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (StringUtils.equals(beanName, "springSocialConfigurer")) {
			SpringSocialConfigurer config = (SpringSocialConfigurer) bean;
			config.signupUrl("/social/signUp");
		}
		return bean;
	}

}
