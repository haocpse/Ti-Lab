package com.haocp.tilab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TilabApplication {

	public static void main(String[] args) {
		SpringApplication.run(TilabApplication.class, args);
	}

}
