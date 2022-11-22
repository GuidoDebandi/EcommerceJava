package com.globallogic.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ProductsApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductsApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
		logger.debug("Logger debug");
		logger.warn("Warn debug");
		logger.error("Error debug");
	}

}
