package leui.woojoo.base;

import leui.woojoo.domain.sms.Sms;
import leui.woojoo.domain.sms.SmsService;
import leui.woojoo.domain.users.dto.web.PhoneNumberRequest;
import leui.woojoo.domain.users.entity.Users;
import leui.woojoo.domain.users.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"test"})
public class Init {
    @Bean
    CommandLineRunner initData(
            AuthService authService,
            SmsService smsService
    ) {
        return args -> {
            Users users = Users.builder()
                    .name("test1")
                    .phoneNumber("1111")
                    .profileImageName("test_image.png")
                    .fcmToken("1234")
                    .build();
            authService.save(users);

            smsService.save("1111", "000000");
        };
    }
}