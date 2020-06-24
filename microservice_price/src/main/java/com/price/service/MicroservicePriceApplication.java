package com.price.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroservicePriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicePriceApplication.class, args);
	}

}
