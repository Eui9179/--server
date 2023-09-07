package leui.woojoo.bounded_context.users.repository;

import leui.woojoo.bounded_context.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT friends_id as friendId FROM users_friends WHERE users_id=:userId", nativeQuery = true)
    List<FriendIds> findFriendIdAllById(@Param("userId") Long userId);
}
