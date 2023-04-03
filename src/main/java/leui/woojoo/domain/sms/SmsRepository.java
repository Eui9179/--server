package leui.woojoo.domain.sms;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsRepository extends JpaRepository<Sms, Long> {
    void deleteByPhoneNumber(String phoneNumber);
    Optional<Sms> findByPhoneNumber(String phoneNumber);
}
