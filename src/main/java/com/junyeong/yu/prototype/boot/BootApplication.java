package com.junyeong.yu.prototype.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer; // this is for resolving jsp page

@SpringBootApplication
public class BootApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BootApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}
}
