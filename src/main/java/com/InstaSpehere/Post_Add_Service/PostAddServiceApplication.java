package com.InstaSpehere.Post_Add_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.InstaSpehere.Post_Add_Service.service")
public class PostAddServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostAddServiceApplication.class, args);
	}

}
