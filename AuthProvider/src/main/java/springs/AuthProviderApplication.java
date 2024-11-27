package springs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class AuthProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthProviderApplication.class, args);
	}

}
