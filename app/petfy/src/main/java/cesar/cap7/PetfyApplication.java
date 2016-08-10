package cesar.cap7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("cesar.cap7.petfy.repository")
public class PetfyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetfyApplication.class, args);
	}
}
