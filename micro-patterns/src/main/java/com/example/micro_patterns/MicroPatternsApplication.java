package com.example.micro_patterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.micro_patterns.sec01")
public class MicroPatternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroPatternsApplication.class, args);
	}

}
