package leui.woojoo.domain.groups.entity;

import leui.woojoo.domain.users.dto.UserSimple;

import java.util.List;

public interface GroupsRepositoryCustom {
    List<UserSimple> findUserByGroup(String groupName);
}
