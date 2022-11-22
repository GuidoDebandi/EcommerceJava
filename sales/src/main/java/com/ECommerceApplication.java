
package com;

import org.junit.jupiter.api.Disabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class ECommerceApplication {

	private static final Logger logger = LoggerFactory.getLogger(ECommerceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
		
		logger.info("Info logger");
		logger.warn("Warn logger");
		logger.error("Error logger");
	}

}
