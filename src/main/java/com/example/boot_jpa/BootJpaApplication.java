package com.example.boot_jpa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.boot_jpa.model.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class BootJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootJpaApplication.class, args);
	}

}