package com.homeinventory.homeinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class HomeInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeInventoryApplication.class, args);
	}


}
