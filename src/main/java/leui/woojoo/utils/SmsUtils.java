package leui.woojoo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import leui.woojoo.domain.users.dto.Messages;
import leui.woojoo.domain.users.dto.web.SmsRequest;
import leui.woojoo.domain.users.dto.web.SmsResponse;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

@Setter
public class SmsUtils {
//    @Value("${sms.serviceId}")
    private String serviceId = "ncp:sms:kr:305396449835:woojoo";

//    @Value("${sms.accessKey}")
    private String accessKey = "ULnSErxzd1AYoGao2wzX";

//    @Value("${sms.secretKey}")
    private String secretKey = "noHGrMlhhf7JER9e1nZtLWeRXownAw5qMqysa0cd";

//    @Value("${sms.caller}")
    private String caller = "01026649179";

    public SmsResponse sendSms(String phoneNumber, String content) throws JsonProcessingException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, URISyntaxException {
        Long time = System.currentTimeMillis();
        List<Messages> messages = new ArrayList<>();
        messages.add(new Messages(phoneNumber, content));

        SmsRequest smsRequest = new SmsRequest("SMS", "COMM", "82", caller, "내용", messages);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(smsRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", this.accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

        HttpEntity<String> body = new HttpEntity<>(jsonBody,headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(
                new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + this.serviceId + "/messages"),
                body,
                SmsResponse.class);
    }

    public String makeSignature(Long time) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        // https://api.ncloud-docs.com/docs/common-ncpapi
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

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
}
