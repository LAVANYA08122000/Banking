package com.lti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.lti")
@EntityScan(value = "com.lti.entity")
public class BankingSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSpringBootApplication.class, args);
	}

}
