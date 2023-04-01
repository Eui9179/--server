package leui.woojoo.domain.users.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT friends_id as friendId FROM users_friends WHERE users_id=:userId", nativeQuery = true)
    List<FriendId> findFriendIdAllById(Long userId);
}
