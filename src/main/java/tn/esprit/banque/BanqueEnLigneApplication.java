package tn.esprit.banque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(scanBasePackages = "tn.esprit.banque")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class BanqueEnLigneApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanqueEnLigneApplication.class, args);
	}

}
