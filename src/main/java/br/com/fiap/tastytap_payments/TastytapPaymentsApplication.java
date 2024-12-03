package br.com.fiap.tastytap_payments;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {@Server(url = "/payments", description = "Default Server URL")})
@SpringBootApplication
public class TastytapPaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TastytapPaymentsApplication.class, args);
	}
}
