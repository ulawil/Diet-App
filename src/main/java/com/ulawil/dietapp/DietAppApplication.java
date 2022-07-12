package com.ulawil.dietapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DietAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(DietAppApplication.class, args);
	}
}
