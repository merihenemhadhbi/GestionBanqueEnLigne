package tn.esprit.banque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "tn.esprit.banque")
public class BanqueEnLigneApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanqueEnLigneApplication.class, args);
	}

}
