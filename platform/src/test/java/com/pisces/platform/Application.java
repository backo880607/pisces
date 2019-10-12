package com.pisces.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pisces.core.utils.AppUtils;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"com.pisces"})
@MapperScan(basePackages={"com.pisces.**.dao", "com.pisces.**.dao.impl"})
public class Application {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		AppUtils.setContext(app.run(args));
    }
}
