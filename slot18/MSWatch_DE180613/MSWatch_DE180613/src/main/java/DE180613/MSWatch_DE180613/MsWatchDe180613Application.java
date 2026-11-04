package DE180613.MSWatch_DE180613;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// [TODO-1.3] Enable OpenFeign clients for Saga orchestration (calling MSBrand participant)
@SpringBootApplication
@EnableFeignClients
public class MsWatchDe180613Application {

    public static void main(String[] args) {
        SpringApplication.run(MsWatchDe180613Application.class, args);
    }

}
