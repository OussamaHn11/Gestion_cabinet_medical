package com.example.Backend_gestion_cabinet_medical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BackendGestionCabinetMedicalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendGestionCabinetMedicalApplication.class, args);
	}

}
