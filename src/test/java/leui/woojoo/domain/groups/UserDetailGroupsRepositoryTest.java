package leui.woojoo.domain.groups;

import leui.woojoo.domain.groups.respository.GroupsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDetailGroupsRepositoryTest {

    @Autowired
    private GroupsRepository groupsRepository;

    @Test
    void UserGroup를_저장하다() {

        //given
        Long userId = 1L;
        String groupName = "인천대학교";
        String detail1 = "4";

        //when
//        UserGroups entity = userGroupsRepository.save(UserGroups.builder()
//                .userId(userId)
//                .groupName(groupName)
//                .detail1(detail1)
//                .build());

        //then
//        assertThat(entity.getId()).isGreaterThan(0L);
//        assertThat(entity.getGroupName()).isEqualTo(groupName);
    }


}
