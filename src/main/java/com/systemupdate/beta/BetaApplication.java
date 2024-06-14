package com.systemupdate.beta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BetaApplication {

	public static void main(String[] args) {
		//System.out.println(new BCryptPasswordEncoder().encode("admin"));
		SpringApplication.run(BetaApplication.class, args);
	}

}
