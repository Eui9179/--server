package leui.woojoo.bounded_context.games.repository;

import jakarta.transaction.Transactional;
import leui.woojoo.bounded_context.games.entity.Games;
import leui.woojoo.bounded_context.users.entity.Users;
import leui.woojoo.bounded_context.users.repository.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class GameRepositoryTest {

    @Autowired
    private GamesRepository gamesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @DisplayName("게임 저장 테스트")
    void t001() {
        //given
        Users user = usersRepository.save(Users.builder()
                .name("test")
                .phoneNumber("+2222")
                .profileImageName(null)
                .fcmToken("1234")
                .build());

        //when
        String gameName = "leagueoflegends";
        String gameNickname = "Lee";
        Games games = gamesRepository.save(Games.builder()
                .users(user)
                .game(gameName)
                .nickname(gameNickname)
                .build());

        //then
        Assertions.assertThat(games.getUsers().getId()).isEqualTo(user.getId());
        Assertions.assertThat(games.getGame()).isEqualTo(gameName);
    }

}
