package com.ecommerce.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication

public class EcommerceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceDemoApplication.class, args);
	}

}
