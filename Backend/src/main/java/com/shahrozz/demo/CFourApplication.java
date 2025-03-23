package com.shahrozz.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CFourApplication {

	public static void main(String[] args) {
		SpringApplication.run(CFourApplication.class, args);
	}

}
