package com.example.bitemeals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories
public class BitemealsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitemealsApplication.class, args);
	}

}
