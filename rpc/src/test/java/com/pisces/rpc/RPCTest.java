package com.pisces.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import com.pisces.core.utils.AppUtils;

@EnableEurekaServer
@SpringBootApplication
public class RPCTest {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RPCTest.class);
		AppUtils.setContext(app.run(args));
    }
}
