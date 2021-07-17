package com.manduScheduler.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.manduScheduler.main.job.ShoppingListScheduler;

@SpringBootApplication
public class ManduSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManduSchedulerApplication.class, args);
	}
}



