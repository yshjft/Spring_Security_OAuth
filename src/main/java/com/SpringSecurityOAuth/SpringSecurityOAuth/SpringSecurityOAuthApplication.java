package com.SpringSecurityOAuth.SpringSecurityOAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringSecurityOAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityOAuthApplication.class, args);
	}

}
