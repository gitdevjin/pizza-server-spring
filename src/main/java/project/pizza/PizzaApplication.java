package project.pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import project.pizza.config.RepositoryConfig;

@Import(RepositoryConfig.class)
@SpringBootApplication
public class PizzaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaApplication.class, args);
	}

}
