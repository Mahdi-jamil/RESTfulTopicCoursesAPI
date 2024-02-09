package com.devesta.curricula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EntityScan(basePackages = "com.devesta.curricula")
@ComponentScan(basePackages = "com.devesta.curricula")
@SpringBootApplication
public class CurriculumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurriculumServiceApplication.class, args);
	}

}

