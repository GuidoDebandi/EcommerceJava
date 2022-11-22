package com.globallogic.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ClientsApplication {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ClientsApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(ClientsApplication.class, args);
		logger.info("Info logger");
		logger.warn("Warn logger");
		logger.error("Error logger");
		
		
	}

}
