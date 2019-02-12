package com.sso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

	@Override
	protected void configureViewResolvers(ViewResolverRegistry registry) {
		registry.viewResolver(new InternalResourceViewResolver());
	}

}
