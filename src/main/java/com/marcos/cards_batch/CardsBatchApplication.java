package com.marcos.cards_batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CardsBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsBatchApplication.class, args);
	}

}
