package leui.woojoo.domain.groups.entity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import leui.woojoo.domain.users.dto.UserSimple;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static leui.woojoo.domain.groups.entity.QGroups.groups;
import static leui.woojoo.domain.users.entity.QUsers.users;

@RequiredArgsConstructor
public class GroupsRepositoryImpl implements GroupsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserSimple> findUserByGroup(String groupName) {
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