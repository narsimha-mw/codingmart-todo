import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.travel.*"})
@EnableJpaRepositories(basePackages = {"com.travel.*"})
@ComponentScan(basePackages = {"com.travel.*"})
//@RestController

public class TravelPassengerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelPassengerApplication.class, args);
    }
//    @GetMapping("/api/msg/hello")
//    public String msg() {
//     return  "Main class is running";
//}
}