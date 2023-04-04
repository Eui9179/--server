package leui.woojoo.domain.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import leui.woojoo.domain.users.dto.web.CpRequest;
import leui.woojoo.domain.users.dto.web.PhoneNumberRequest;
import leui.woojoo.domain.users.dto.web.SmsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class SmsController {

    private final SmsUtils smsUtils;
    private final SmsService smsService;

    @PostMapping("/sms")
    public SmsResponse sendSms(@RequestBody PhoneNumberRequest phoneNumber) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        String cp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        log.info("cp = {}", cp);
        log.info("phoneNumber = {}", phoneNumber.getPhoneNumber());
        smsService.save(phoneNumber.getPhoneNumber(), cp);

        return smsUtils.sendSms(phoneNumber.getPhoneNumber(), smsUtils.genCpText(cp));
    }

    @PostMapping("/sms-auth")
    public ResponseEntity<String> authenticateSms(@RequestBody CpRequest cpRequest) {
        log.info("phoneNumber = {}", cpRequest.getPhoneNumber());
        log.info("cpInRequest = {}", cpRequest.getCp());
        if (smsService.verify(cpRequest.getPhoneNumber(), cpRequest.getCp())) {
            log.info("cp success? -> yes");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        log.info("cp success? -> no");
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
