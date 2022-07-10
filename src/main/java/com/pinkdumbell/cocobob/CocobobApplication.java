package com.pinkdumbell.cocobob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CocobobApplication {
	@RequestMapping("/")
	public String home() {
		return "Hello CoCoBob Backend";
	}
	public static void main(String[] args) {
		SpringApplication.run(CocobobApplication.class, args);
	}
}
