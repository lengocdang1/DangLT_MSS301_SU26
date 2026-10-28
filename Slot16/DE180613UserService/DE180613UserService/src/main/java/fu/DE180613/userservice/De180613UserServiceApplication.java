package fu.DE180613.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "fu.DE180613")
@EnableFeignClients(basePackages = "fu.DE180613.postservice.client")
@EnableJpaRepositories(basePackages = "fu.DE180613")
@EntityScan(basePackages = "fu.DE180613")
public class De180613UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(De180613UserServiceApplication.class, args);
	}

}
