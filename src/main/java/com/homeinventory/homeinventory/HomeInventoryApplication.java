package com.homeinventory.homeinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
public class HomeInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeInventoryApplication.class, args);
	}

	@CrossOrigin
	@RequestMapping("/inventory/api/v1/authenticate")
	public Principal user(Principal user){
		return user;
	}

}
