package leui.woojoo.bounded_context.groups.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupsRepository extends JpaRepository<Groups, Long> {
    List<Groups> findAllByGroupName(String groupName);

    List<Groups> findAllByGroupNameAndDetail1(String groupName, String detail1);
}