package com.msf.TaSk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
public class TaSkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaSkApplication.class, args);
	}
//@Bean
//	public PasswordEncoder passwordEncoder()
//{
//		return NoOpPasswordEncoder.getInstance();}
}
