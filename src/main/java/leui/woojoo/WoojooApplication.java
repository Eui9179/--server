package leui.woojoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@PropertySource(value = {"application-sms.properties"})
public class WoojooApplication {
    public static void main(String[] args) {
        SpringApplication.run(WoojooApplication.class, args);
    }
}
