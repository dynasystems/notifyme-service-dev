package com.notifyme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class NotifyMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifyMeApplication.class, args);
	}

}
