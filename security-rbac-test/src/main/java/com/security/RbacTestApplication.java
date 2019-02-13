package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.security","com.imooc"})
public class RbacTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RbacTestApplication.class, args);
	}

}
