package com.travel.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
public class TravelEurekaApplication {

	public static void main(String[] args) {
		// Will configure using travel-eureka-client.yml
		System.setProperty("spring.config.name", "travel-eureka-client");
		SpringApplication.run(TravelEurekaApplication.class, args);
	}

}
