package com.oliver.retrofit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class RetrofitApplication implements CommandLineRunner {

	@Autowired
	private CreaTablaPersona creaTablaPersona;

	public static void main(String[] args) {
		SpringApplication.run(RetrofitApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		creaTablaPersona.creaTablaPersona();
	}
}
