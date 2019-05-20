package com.pisces.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pisces.core.utils.AppUtils;

@SpringBootApplication
public class AppTest {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppTest.class);
		AppUtils.setContext(app.run(args));
    }
}
