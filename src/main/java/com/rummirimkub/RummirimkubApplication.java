package com.rummirimkub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(scanBasePackages = "com.rummirimkub")
public class RummirimkubApplication {

	public static void main(String[] args) {
		SpringApplication.run(RummirimkubApplication.class, args);
	}

}
