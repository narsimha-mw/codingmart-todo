package com.user.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
public class OauthTokenUserMicroserverApplication {

	public static void main(String[] args) {
		// Will configure using oauth-token-client.yml
        System.setProperty("spring.config.name", "oauth-token");
		SpringApplication.run(OauthTokenUserMicroserverApplication.class, args);
	}

}
