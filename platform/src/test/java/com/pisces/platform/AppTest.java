package com.pisces.platform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.core.utils.AppUtils;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"com.pisces"})
@MapperScan(basePackages={"com.pisces.*.dao"})
@RestController
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class AppTest {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppTest.class);
		AppUtils.setContext(app.run(args));
    }
	
	@Value("${hello}")
	String hello;
	@RequestMapping(value = "/hello")
	public String hello(){
		return hello;
	}
}
