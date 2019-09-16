package com.pisces.core.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pisces.core.utils.AppUtils;

@SpringBootApplication(scanBasePackages = {"com.pisces", "${app.base-packages:}.**"})
public class Application {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		AppUtils.setContext(app.run(args));
    }
}
