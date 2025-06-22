package com.eduvod.eduvod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EduvodApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduvodApplication.class, args);
	}

}
