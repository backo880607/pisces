package com.pisces.algorithm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pisces.core.util.AppUtil;

@SpringBootApplication
public class AppTest {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppTest.class);
		AppUtil.setContext(app.run(args));
    }
}
