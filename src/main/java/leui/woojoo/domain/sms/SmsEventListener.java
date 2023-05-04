package leui.woojoo.domain.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import leui.woojoo.base.event.EventSendSms;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class SmsEventListener {

    private final SmsService smsService;

    @Async
    @EventListener
    public void listen(EventSendSms event) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        smsService.sendSms(event.getPhoneNumber(), event.getCp());
    }
}
