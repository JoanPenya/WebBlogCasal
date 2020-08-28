package com.iesemilidarder.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.iesemilidarder.springboot.app.service.IUploadFileService;

@SpringBootApplication
public class EspaisJovesApplication implements CommandLineRunner{
	
	@Autowired
	IUploadFileService uploadFileService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(EspaisJovesApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		String password = "hola123";
		
		for(int i=0; i<5; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
		}
	}

}
