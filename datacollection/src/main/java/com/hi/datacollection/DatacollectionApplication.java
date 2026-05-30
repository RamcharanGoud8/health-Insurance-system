package com.hi.datacollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DatacollectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatacollectionApplication.class, args);
	}

}
