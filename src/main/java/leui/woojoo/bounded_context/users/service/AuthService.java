package leui.woojoo.bounded_context.users.service;

import leui.woojoo.DataNotFoundException;
import leui.woojoo.bounded_context.users.entity.Users;
import leui.woojoo.bounded_context.users.repository.UsersRepository;
import leui.woojoo.bounded_context.users.dto.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class AuthService {
    final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public UserDetail findById(Long id) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없다 " + id));
        return new UserDetail(users);
    }

    @Transactional(readOnly = true)
    public UserDetail findByPhoneNumber(String phoneNumber) {
        Users users = usersRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
        return users == null ? null : new UserDetail(users);
    }

    public Users save(Users users) {
        return usersRepository.save(users);
    }

    public void updateFcmToken(Long userId, String fcmToken) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다. " + userId));
        users.asyncFcmToken(fcmToken);
    }

    @Transactional
    public String deleteUser(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("유저가 없습니다."));

        user.clearRelationship();
        usersRepository.delete(user);

        return user.getProfileImageName();
    }
}
