package com.example.AppInstallationSystem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling // enables Spring's task scheduling mechanism, allowing the use of @Scheduled annotations in the application.
public class AppInstallationSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AppInstallationSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("App Installation System started successfully.");
	}

}
