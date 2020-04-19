package com.trp.learningapp.api.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.qos.logback.classic.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LearningAppApiUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningAppApiUsersApplication.class, args);
	}
	 
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	feign.Logger.Level feignLoggerLevel()
	{
		return feign.Logger.Level.FULL;
	}
}
