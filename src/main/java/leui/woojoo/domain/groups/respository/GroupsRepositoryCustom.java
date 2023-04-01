package leui.woojoo.domain.groups.respository;

import leui.woojoo.domain.users.Users;
import leui.woojoo.domain.users.dto.UserSimple;

import java.util.List;

public interface GroupsRepositoryCustom {
    List<UserSimple> findUserByGroup(String groupName);
}
