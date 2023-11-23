package com.muratozgon.online.store.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@ComponentScan({ "com.muratozgon.online.store.service.*" })
@EnableEurekaClient
public class OnlineStoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreServiceApplication.class, args);
	}

}
