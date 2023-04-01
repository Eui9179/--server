package leui.woojoo.domain.games;

import leui.woojoo.domain.games.entity.Games;
import leui.woojoo.domain.games.entity.GamesRepository;
import leui.woojoo.domain.users.entity.Users;
import leui.woojoo.domain.users.entity.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
    void  user_game을_데이터베이스에_저장하다() {
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
