package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.security.controller";
	public static final String VERSION = "1.0.0";
	
	ApiInfo apiInfo(){
		return new ApiInfoBuilder()
			.title("swagger测试")
			.description("这是swagger框架测试")
			.license("Apache 2.0")
			.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
			.termsOfServiceUrl("")
			.version(VERSION)
			.contact(new Contact("", "", ""))
			.build();
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
			.paths(PathSelectors.any())
			.build()
			.apiInfo(apiInfo());
	}
}
