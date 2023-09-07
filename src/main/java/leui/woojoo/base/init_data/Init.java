package leui.woojoo.base.init_data;

import leui.woojoo.bounded_context.games.service.GamesService;
import leui.woojoo.bounded_context.sms.service.SmsService;
import leui.woojoo.bounded_context.users.entity.Users;
import leui.woojoo.bounded_context.users.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile({"test"})
public class Init {
    @Bean
    CommandLineRunner initData(
            AuthService authService,
            SmsService smsService,
            GamesService gamesService
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

            gamesService.updateGameList(users.getId(), List.of("leagueoflegends", "overwatch"));
        };
    }
}