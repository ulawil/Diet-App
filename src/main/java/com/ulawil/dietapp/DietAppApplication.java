package com.ulawil.dietapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@SpringBootApplication
public class DietAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(DietAppApplication.class, args);
	}
}
