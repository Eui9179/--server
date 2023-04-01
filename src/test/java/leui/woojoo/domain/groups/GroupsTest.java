package leui.woojoo.domain.groups;

import leui.woojoo.domain.groups.respository.GroupsRepositoryImpl;
import leui.woojoo.domain.users.dto.UserSimple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class GroupsTest {

    @Autowired
    private GroupsRepositoryImpl groupsRepository;

    @Test
    void t1() {
        List<UserSimple> users = groupsRepository.findUserByGroup("hello");
        Assertions.assertEquals(4, users.size());

    }
}
