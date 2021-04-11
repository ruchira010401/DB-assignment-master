package com.trade.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeManagementApplication.class, args);
	}

}
