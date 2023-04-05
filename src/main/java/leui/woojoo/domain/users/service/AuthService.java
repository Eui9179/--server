package leui.woojoo.domain.users.service;

import leui.woojoo.domain.users.entity.Users;
import leui.woojoo.domain.users.entity.UsersRepository;
import leui.woojoo.domain.users.dto.UserDetail;
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

    public String deleteUser(Long userId) {
        Users entity = usersRepository.findById(userId).orElse(null);
        if (entity == null) {
            return null;
        }
        usersRepository.delete(entity);
        return entity.getProfileImageName();
    }
}
