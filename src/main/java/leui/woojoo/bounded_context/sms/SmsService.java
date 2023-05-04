package leui.woojoo.bounded_context.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import leui.woojoo.DataNotFoundException;
import leui.woojoo.base.event.EventSendSms;
import leui.woojoo.bounded_context.users.dto.Messages;
import leui.woojoo.bounded_context.users.dto.web.SmsRequest;
import leui.woojoo.bounded_context.users.dto.web.SmsResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class SmsService {

    @Value("${sms.serviceId}")
    private String serviceId;
    @Value("${sms.accessKey}")
    private String accessKey;
    @Value("${sms.secretKey}")
    private String secretKey;
    @Value("${sms.caller}")
    private String caller;
    private final SmsRepository smsRepository;
    private final ApplicationEventPublisher publisher;

    public void sendAuthCode(String phoneNumber) {
        String cp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        save(phoneNumber, cp);
        publisher.publishEvent(new EventSendSms(this, phoneNumber, genCpText(cp)));
    }

    @Transactional
    public void save(String phoneNumber, String cp) {
        Optional<Sms> oSmsAuthInfo = smsRepository.findByPhoneNumber(phoneNumber);
        if (oSmsAuthInfo.isPresent()) {
            oSmsAuthInfo.get().updateCp(cp);
            return;
        }
        smsRepository.save(Sms.builder()
                .phoneNumber(phoneNumber)
                .cp(cp)
                .build());
    }

    @Transactional
    public void delete(String phoneNumber) {
        smsRepository.deleteByPhoneNumber(phoneNumber);
    }

    public boolean verify(String phoneNumber, String cp) {
        Sms sms = smsRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataNotFoundException("해당 유저가 없습니다."));
        return sms.getCp().equals(cp);
    }


    public void sendSms(String phoneNumber, String content) throws JsonProcessingException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, URISyntaxException {
        Long time = System.currentTimeMillis();
        List<Messages> messages = new ArrayList<>();
        messages.add(new Messages(phoneNumber, genCpText(content)));

        SmsRequest smsRequest = new SmsRequest("SMS", "COMM", "82", caller, "내용", messages);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(smsRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

        HttpEntity<String> body = new HttpEntity<>(jsonBody,headers);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(
                new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"),
                body,
                SmsResponse.class);

    }

    public String makeSignature(Long time) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ serviceId+"/messages";
        String timestamp = time.toString();

        String message = method +
                space +
                url +
                newLine +
                timestamp +
                newLine +
                accessKey;

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeBase64String(rawHmac);
    }

    public String genCpText(String cp) {
        return "[우리주변게임친구]\n본인확인 인증번호는 [" + cp + "]입니다.";
    }
}
