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
    private final SmsService smsService;

    @PostMapping("/sms")
    public String sendSms(@RequestBody PhoneNumberRequest phoneNumber) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        smsService.send(phoneNumber.getPhoneNumber());
        return "ok";
    }

    @PostMapping("/sms-auth")
    public ResponseEntity<String> authenticateSms(@RequestBody CpRequest cpRequest) {
        log.info("cpInRequest = {}", cpRequest.getCp());
        if (smsService.verify(cpRequest.getPhoneNumber(), cpRequest.getCp())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
