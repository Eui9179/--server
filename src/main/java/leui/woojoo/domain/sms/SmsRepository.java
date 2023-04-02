package leui.woojoo.domain.sms;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository<Sms, Long> {
    void deleteByPhoneNumber(String phoneNumber);
    Sms findByPhoneNumber(String phoneNumber);
}
