package com.user.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
public class OauthTokenApplication {

	public static void main(String[] args) {
		// Will configure using oauth-token-client.yml
        System.setProperty("spring.config.name", "oauth-token");
        System.out.println("MNBHJBMB");
		SpringApplication.run(OauthTokenApplication.class, args);
	}

}
