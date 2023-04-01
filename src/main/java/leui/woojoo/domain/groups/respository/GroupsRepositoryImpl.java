package leui.woojoo.domain.groups.respository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import leui.woojoo.domain.groups.Groups;
import leui.woojoo.domain.users.QUsers;
import leui.woojoo.domain.users.Users;
import leui.woojoo.domain.users.dto.UserSimple;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static leui.woojoo.domain.groups.QGroups.groups;
import static leui.woojoo.domain.users.QUsers.users;

@RequiredArgsConstructor
public class GroupsRepositoryImpl implements GroupsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserSimple> findUserByGroup(String groupName) {
        /**
         * select u.id, u.name, u.profile_image_name
         * from groups as g
         * inner join users as u
         * on g.group_name = "hello" and u.id = g.users_id;
         */
        return queryFactory
                .select(Projections.fields(UserSimple.class,
                        users.id.as("userId"),
                        users.name,
                        users.profileImageName)
                ).from(groups)
                .innerJoin(groups.users, users)
                .on(groups.groupName.eq(groupName))
                .on(users.id.eq(groups.id))
                .fetch();

    }
}