package leui.woojoo.domain.users;

import leui.woojoo.domain.users.entity.Users;
import leui.woojoo.domain.users.entity.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDetailRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @AfterEach
    void cleanUp() {
        usersRepository.deleteAll();
    }

    @Test
    void user를_데이터베이스에_저장하다() {
        //given
        String name = "leui";
        String phoneNumber = "+821026649179";
        String profileImageName = "default.png";
        String fcmToken = "1234";

        //when
        Users entity = usersRepository.save(Users.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .profileImageName(profileImageName)
                .fcmToken(fcmToken)
                .build());

        //then
        List<Users> userList = usersRepository.findAll();
        assertThat(userList.get(0).getId()).isGreaterThan(0L);
        assertThat(userList.get(0).getName()).isEqualTo(name);
    }
}
