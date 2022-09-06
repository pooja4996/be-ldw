package com.wsa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TrafficCountApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(TrafficCountApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TrafficCountApplication.class);
	}
	
	

}
