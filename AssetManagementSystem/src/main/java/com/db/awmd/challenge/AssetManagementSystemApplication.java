package com.db.awmd.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.db.awmd.challenge.*")
@EntityScan("com.db.awmd.challenge.service.*")
public class AssetManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetManagementSystemApplication.class, args);
	}

}
