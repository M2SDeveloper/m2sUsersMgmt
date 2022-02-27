package com.m2s;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class M2swebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(M2swebserviceApplication.class, args);
	}

}
