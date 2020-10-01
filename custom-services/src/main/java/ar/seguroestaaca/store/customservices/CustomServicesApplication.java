package ar.seguroestaaca.store.customservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CustomServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomServicesApplication.class, args);
	}

}
