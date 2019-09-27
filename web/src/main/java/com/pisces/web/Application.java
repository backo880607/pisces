package com.pisces.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pisces.core.utils.AppUtils;

@SpringBootApplication(scanBasePackages = {"com.pisces"})
//@MapperScan(basePackages={"com.pisces.*.dao"})
public class Application {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		AppUtils.setContext(app.run(args));
    }
}
