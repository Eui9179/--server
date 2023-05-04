package leui.woojoo.bounded_context.games;

import leui.woojoo.bounded_context.games.entity.Games;
import leui.woojoo.bounded_context.games.entity.GamesRepository;
import leui.woojoo.bounded_context.users.entity.Users;
import leui.woojoo.bounded_context.users.entity.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDetailGameRepositoryTest {

    @Autowired
    private GamesRepository gamesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @AfterEach
    void cleanUp() {
        gamesRepository.deleteAll();
    }

    @Test
    @DisplayName("게임 저장 테스트")
    void t001() {
        //given
        Users userEntity = usersRepository.save(Users.builder()
                .name("test")
                .phoneNumber("+1026649179")
                .profileImageName("default.png")
                .fcmToken("1234")
                .build());

        String gameName = "leagueoflegneds";
        String gameNickname = "Lee";

        //when
        Games gamesEntity = gamesRepository.save(Games.builder()
                .game(gameName)
                .nickname(gameNickname)
                .build());

        //then
        Assertions.assertThat(gamesEntity.getUsers().getId()).isEqualTo(userEntity.getId());
        Assertions.assertThat(gamesEntity.getGame()).isEqualTo(gameName);
    }

}
