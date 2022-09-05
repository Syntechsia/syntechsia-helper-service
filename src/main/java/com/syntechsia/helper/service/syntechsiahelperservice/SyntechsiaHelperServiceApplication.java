package com.syntechsia.helper.service.syntechsiahelperservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SyntechsiaHelperServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyntechsiaHelperServiceApplication.class, args);
	}

}
