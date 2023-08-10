package com.furguard.backend;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Tag(name = "Welcome Controller")
public class FurGuardMobileBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FurGuardMobileBackendApplication.class, args);
	}

	@Value("${server.port}")
	private int serverPost;

	@GetMapping("/")
	public String welcomeMessage(){
		return "FurGuard Backend Running Well on Port " + serverPost;
	}
}
